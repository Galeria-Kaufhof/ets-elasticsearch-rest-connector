package de.kaufhof.ets.elasticsearchrestconnector.core.stream

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{Materializer, OverflowStrategy}
import akka.stream.scaladsl.{Flow, Source}
import com.typesafe.scalalogging.LazyLogging

import scala.collection.immutable
import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import scala.concurrent.{ExecutionContext, Future}

trait ElasticSearchResultAsStreamScroll[Params, InternalSearchResult, Result] extends LazyLogging {

  case class Page(content: Seq[Result], scrollIdOpt: Option[ScrollId])

  case class CurrentPageInfo(scroll_id: Option[ScrollId], isLastPage: Boolean, isFirstPage: Boolean)

  implicit val ec: ExecutionContext
  implicit val actorSystem: ActorSystem
  implicit val materializer: Materializer

  protected def pageSize: Int = 100

  protected def bufferSize: Int = 4

  protected def tokenValidationTime: TokenValidationTime = TokenValidationTime(Duration(1, MINUTES))

  def getScrollSourceStream(parameters: Params): Source[Result, NotUsed] = {

    Source.unfoldAsync(CurrentPageInfo(scroll_id = None, isLastPage = false, isFirstPage = true)) { currentPage =>
      if (currentPage.isLastPage) {
        Future.successful(None)
      } else {
        fetchPage(parameters, currentPage).map { page: Page =>
          Some(
            (
              CurrentPageInfo(
                scroll_id = page.scrollIdOpt,
                 isLastPage = page.content.isEmpty,
                isFirstPage = false
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

  private def fetchPage(parameters: Params, currentPageInfo: CurrentPageInfo): Future[Page] = {
    logger.info("fetch a page from elasticsearch")
    if(currentPageInfo.isFirstPage) {
      searchFirst(parameters).map(generateResult)
    } else {
      searchContinously(currentPageInfo).map(generateResult)
    }
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

  protected def generateResult(internalSearchResult: InternalSearchResult): Page

  protected def searchFirst(params: Params): Future[InternalSearchResult]

  protected def searchContinously(currentPageInfo: CurrentPageInfo): Future[InternalSearchResult]

}
