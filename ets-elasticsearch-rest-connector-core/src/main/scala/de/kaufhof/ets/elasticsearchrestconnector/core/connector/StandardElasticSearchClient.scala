package de.kaufhof.ets.elasticsearchrestconnector.core.connector

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model._
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.hits.ElasticSearchHits
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.indexing.{BulkInsertResultItem, IndexedDocument}
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.MappingObject
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.percolate.PercolateDocument
import de.kaufhof.ets.elasticsearchrestconnector.core.client.services.JsonToMappingResultConverter
import org.elasticsearch.client.RestClient
import play.api.libs.json._

import scala.concurrent.{ExecutionContext, Future}


class StandardElasticSearchClient(restClient: RestClient)(implicit val ec: ExecutionContext) {

  import RichRestClient._

  private final val emptyBody: JsValue = Json.obj()
  val compressCommunication: Boolean = true

  def closeClient(): Unit = {
    restClient.close()
  }

  def search(indexName: String, documentType: String, query: QueryObject): Future[ElasticSearchResult] = {
    val endpoint: String = s"/${urlEncodePathEntity(indexName)}/$documentType/_search"
    restClient.postJson(endpoint, query.asJsObject, compressCommunication).map(JsonToSearchResultConverter(_)).recover {
      case ex: Throwable =>
        ElasticSearchResult(
          took = -1,
          hits = ElasticSearchHits(Seq.empty),
          total = 0,
          aggregations = None,
          hasError = true,
          errorMessage = ex.getMessage + "\n\n" + query
        )
    }
  }

  def getMapping(indexName: String, documentType: String): Future[ElasticMappingReceiveResult] = {
    val endpoint: String = s"/${urlEncodePathEntity(indexName)}/$documentType/_mapping"
    restClient.getJson(endpoint, compressCommunication).map(JsonToMappingResultConverter(_)).recover {
      case ex: Throwable =>
        ElasticMappingReceiveResult(
          hasError = true,
          errorMessage = ex.getMessage
        )
    }

  }

  private def urlEncodePathEntity(indexName: String): String = java.net.URLEncoder.encode(indexName, "utf-8")

  def createIndex(indexName: String, mappingObject: MappingObject): Future[ElasticMappingCreateResult] = {
    createIndex(indexName, mappingObject.asJsObject)
  }

  def createIndex(indexName: String, mappingAsJson: JsValue): Future[ElasticMappingCreateResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}"
    restClient.putJson(endpoint, mappingAsJson, compressCommunication).map { jsResult =>
      ElasticMappingCreateResult(
        created = (jsResult \ "acknowledged").asOpt[Boolean].getOrElse(false)
      )
    } recover {
      case ex: Throwable =>
        println(s"error adding $ex")
        ElasticMappingCreateResult(
          hasError = true,
          errorMessage = ex.getMessage,
          created = false
        )
    }
  }

  def refreshIndex(indexName: String): Future[ElasticIndexRefreshResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/_refresh"
    restClient.postJson(endpoint, emptyBody, compressCommunication).map { jsResult =>
      val u = jsResult \\ "successful"
      val v = jsResult \\ "total"
      val f: Option[Int] = (jsResult \\ "failed").headOption.flatMap(_.asOpt[Int])
      ElasticIndexRefreshResult(
        refreshed = f.contains(0)
      )
    }.recover {
      case ex: Throwable =>
        ElasticIndexRefreshResult(
          hasError = true,
          errorMessage = ex.getMessage,
          refreshed = false
        )
    }
  }

  def flushIndex(indexName: String): Future[ElasticIndexFlushResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/_flush"
    restClient.postJson(endpoint, emptyBody, compressCommunication).map { jsResult =>
      val u = jsResult \\ "successful"
      val v = jsResult \\ "total"
      val f = (jsResult \\ "failed").headOption.flatMap(_.asOpt[Int])
      ElasticIndexFlushResult(
        flushed = f.contains(0)
      )
    }.recover {
      case ex: Throwable =>
        ElasticIndexFlushResult(
          hasError = true,
          errorMessage = ex.getMessage,
          flushed = false
        )
    }
  }

  def deleteIndex(indexName: String): Future[ElasticIndexDeleteResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}"
    restClient.delete(endpoint, compressCommunication).map { jsResult =>
      ElasticIndexDeleteResult(
        deleted = (jsResult \ "acknowledged").asOpt[Boolean].getOrElse(false)
      )
    }.recover {
      case ex: Throwable =>
        ElasticIndexDeleteResult(
          hasError = true,
          deleted = false,
          errorMessage = ex.getMessage
        )
    }
  }

  def indexExists(indexName: String): Future[ElasticIndexExistsResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}"
    restClient.headRequest(endpoint).map { jsResult =>
      ElasticIndexExistsResult(
        exists = (jsResult \ "exists").asOpt[Boolean].getOrElse(false)
      )
    }.recover {
      case ex: Throwable =>
        ElasticIndexExistsResult(
          hasError = true,
          errorMessage = ex.getMessage,
          exists = false
        )
    }
  }

  def bulkInsertDocuments(documents: List[IndexedDocument]): Future[ElasticBulkInsertResult] = {
    val endpoint: String = s"/_bulk"
    val bodyContent: String = documents.map { indexedDocument =>
      Json.stringify(indexedDocument.docHeader) + "\n" + Json.stringify(indexedDocument.document)
    } mkString "\n"
    restClient.putJson(endpoint, bodyContent + "\n", compressed = compressCommunication).map { jsResult =>
      ElasticBulkInsertResult(jsResult.as[JsObject])
    }.recover {
      case ex: Throwable =>
        ElasticBulkInsertResult(
          hasError = true,
          errorMessage = ex.getMessage,
          took = 0,
          errors = true,
          items = List.empty[BulkInsertResultItem]
        )
    }
  }

  def deleteDocument(indexName: String, documentType: String, id: String): Future[ElasticDeleteDocumentResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/$documentType/$id"
    restClient.delete(endpoint, compressCommunication).map { jsResult =>
      ElasticDeleteDocumentResult(
        found = (jsResult \ "found").asOpt[Boolean].getOrElse(false)
      )
    }.recover {
      case ex: Throwable =>
        ElasticDeleteDocumentResult(
          hasError = true,
          errorMessage = ex.getMessage,
          found = false
        )
    }
  }

  def registerPercolatorQuery(indexName: String, percolateDocument: PercolateDocument): Future[ElasticPercolateRegisterResult] = {
    val typeName = "variant"
    val endpoint: String = s"/${urlEncodePathEntity(indexName)}/$typeName/${urlEncodePathEntity(percolateDocument.id)}"
    val document = percolateDocument.document + ("type" -> JsString("percolator"))
    restClient.postJson(endpoint, document, compressCommunication).map { jsValue =>
      ElasticPercolateRegisterResult(
        created = (jsValue \ "result").asOpt[String].exists("created" == ),
        version = (jsValue \ "_version").asOpt[Int].getOrElse(0),
        id = (jsValue \ "_id").asOpt[String].getOrElse("")
      )
    } recover {
      case ex: Throwable =>
        ElasticPercolateRegisterResult(
          hasError = true,
          errorMessage = ex.getMessage,
          created = false,
          version = 0,
          id = ""
        )
    }
  }

  def unregisterPercolatorQuery(indexName: String, id: String): Future[ElasticPercolateUnregisterResult] = {
    val typeName: String = "variant"
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/$typeName/${urlEncodePathEntity(id)}"
    restClient.delete(endpoint, compressCommunication).map { jsResult =>
      ElasticPercolateUnregisterResult(
        found = (jsResult \ "result").asOpt[String].contains("deleted")
      )
    } recover {
      case ex: Throwable =>
        ElasticPercolateUnregisterResult(
          hasError = true,
          errorMessage = ex.getMessage,
          found = false
        )
    }
  }

  def findRegisteredQueries(indexName: String, typeName: String, document: JsValue): Future[ElasticPercolateResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/_search"

    restClient.getJson(endpoint, document, compressCommunication).map { jsResult =>
      ElasticPercolateResult(
        matches = extractPercolateResult(jsResult),
        total = (jsResult \ "hits" \ "total").asOpt[Int].getOrElse(0)
      )
    }.recover {
      case ex: Throwable =>
        ElasticPercolateResult(
          hasError = true,
          errorMessage = ex.getMessage,
          matches = List.empty[ElasticPercolateResultMatch],
          total = 0
        )
    }
  }

  private def extractPercolateResult(jsResult: JsValue): List[ElasticPercolateResultMatch] = {
    (jsResult \ "hits" \ "hits").asOpt[JsArray].map { values =>
      values.value.map { inner =>
        ElasticPercolateResultMatch(
          score = (inner \ "_score").asOpt[Float].getOrElse(0),
          percolateMatch = (inner \ "_id").asOpt[String].getOrElse("")
        )
      }.toList
    }.getOrElse(List.empty[ElasticPercolateResultMatch])
  }

}
