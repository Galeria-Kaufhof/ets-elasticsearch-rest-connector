package de.kaufhof.ets.elasticsearchrestconnector.core.stream

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{Materializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Source}
import com.typesafe.scalalogging.LazyLogging

import scala.collection.immutable
import scala.concurrent.{ExecutionContext, Future}

/** provides functions to get an elasticsearch result paged or from outside as a Source[Element].
  *
  * @tparam Params               defines the parameter for the search e.g. QueryParameter, Tuple etc.
  * @tparam InternalSearchResult defines the inner result of the search-method
  * @tparam Result               defines the resultset to work with
  */
trait ElasticSearchResultAsStream[Params, InternalSearchResult, Result] extends LazyLogging {

  case class Page(pageNumber: Int, totalHits: Long, content: Seq[Result])

  case class PagedResult(position: Int, isLastPage: Boolean)

  implicit val ec: ExecutionContext
  implicit val actorSystem: ActorSystem
  implicit val materializer: Materializer

  protected def pageSize: Int = 100
  protected def bufferSize: Int = 4
  protected def start: Int = 0

  def getPagedSourceStream(parameters: Params): Source[Result, NotUsed] = {
    Source.unfoldAsync(PagedResult(position = start, isLastPage = false)) { pageNum =>
      if (pageNum.isLastPage) {
        Future.successful(None)
      } else {
        fetchPage(parameters, pageNum.position).map { page: Page =>
          Some(
            (
              PagedResult(
                position = pageNum.position + 1,
                isLastPage = page.pageNumber + 1 >= calculatePages(page.totalHits, pageSize)
              ), page
            )
          )
        }
      }
    }
      .buffer(bufferSize, OverflowStrategy.backpressure)
      .via(extractItems)
      .via(toImmutableCollectionFlow)
      .via(toSingleElement)
  }

  private val extractItems: Flow[Page, Seq[Result], NotUsed] = {
    Flow[Page].map(_.content)
  }

  private val toImmutableCollectionFlow: Flow[Seq[Result], immutable.Seq[Result], NotUsed] = {
    Flow[Seq[Result]].map(toImmutableCollection)
  }

  private val toSingleElement: Flow[immutable.Seq[Result], Result, NotUsed] = {
    Flow[immutable.Seq[Result]].mapConcat(identity)
  }

  private def toImmutableCollection(col: Seq[Result]): immutable.Seq[Result] = {
    immutable.Seq[Result](col: _*)
  }

  private def fetchPage(parameters: Params, pageNumber: Int): Future[Page] = {
    logger.info(s"fetch a page from ElasticSearch #${pageNumber + 1} with size $pageSize")
    search(parameters, pageNumber * pageSize).map(res => generateResult(res, pageNumber))
  }

  protected def generateResult(internalSearchResult: InternalSearchResult, pageNumber: Int): Page

  protected def search(params: Params, start: Int): Future[InternalSearchResult]

  private def calculatePages(total: Long, size: Int): Int = {
    math.ceil(total.toDouble / size.toDouble).toInt
  }
}