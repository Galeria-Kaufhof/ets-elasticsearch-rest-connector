package de.kaufhof.ets.elasticsearchrestconnector.core.connector

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model._
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.hits.ElasticSearchHits
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.indexing.{BulkOperationResult, IndexedDocument}
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.MappingObject
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.percolate.PercolateDocument
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.DeleteDocumentByQueryWrapper
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes.Query
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results._
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
          throwable = Some(ex)
        )
    }
  }

  def getMapping(indexName: String, documentType: String): Future[ElasticMappingReceiveResult] = {
    val endpoint: String = s"/${urlEncodePathEntity(indexName)}/$documentType/_mapping"
    restClient.getJson(endpoint, compressCommunication).map(JsonToMappingResultConverter(_)).recover {
      case ex: Throwable =>
        ElasticMappingReceiveResult(
          throwable = Some(ex)
        )
    }

  }

  private def urlEncodePathEntity(indexName: String): String = java.net.URLEncoder.encode(indexName, "utf-8")

  def createIndex(mappingObject: MappingObject): Future[ElasticIndexCreateResult] = {
    createIndex(mappingObject.indexName, mappingObject.asJsObject)
  }

  def createIndex(indexName: String, mappingAsJson: JsObject): Future[ElasticIndexCreateResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}"
    restClient.putJson(endpoint, mappingAsJson, compressCommunication).map { jsResult =>
      ElasticIndexCreateResult(
        throwable = None,
        created = (jsResult \ "acknowledged").asOpt[Boolean].getOrElse(false)
      )
    } recover {
      case ex: Throwable =>
        ElasticIndexCreateResult(
          throwable = Some(ex),
          created = false
        )
    }
  }

  def refreshIndex(indexName: String): Future[ElasticIndexRefreshResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/_refresh"
    restClient.postJson(endpoint, emptyBody, compressCommunication).map { jsResult =>
      val f: Option[Int] = (jsResult \\ "failed").headOption.flatMap(_.asOpt[Int])
      ElasticIndexRefreshResult(
        throwable = None,
        refreshed = f.contains(0)
      )
    }.recover {
      case ex: Throwable =>
        ElasticIndexRefreshResult(
          throwable = Some(ex),
          refreshed = false
        )
    }
  }

  def flushIndex(indexName: String): Future[ElasticIndexFlushResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/_flush"
    restClient.postJson(endpoint, emptyBody, compressCommunication).map { jsResult =>
      val f = (jsResult \\ "failed").headOption.flatMap(_.asOpt[Int])
      ElasticIndexFlushResult(
        throwable = None,
        flushed = f.contains(0)
      )
    }.recover {
      case ex: Throwable =>
        ElasticIndexFlushResult(
          throwable = Some(ex),
          flushed = false
        )
    }
  }

  def deleteIndex(indexName: String): Future[ElasticIndexDeleteResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}"
    restClient.delete(endpoint, compressCommunication).map { jsResult =>
      ElasticIndexDeleteResult(
        throwable = None,
        deleted = (jsResult \ "acknowledged").asOpt[Boolean].getOrElse(false)
      )
    }.recover {
      case ex: Throwable =>
        ElasticIndexDeleteResult(
          deleted = false,
          throwable = Some(ex)
        )
    }
  }

  def indexExists(indexName: String): Future[ElasticIndexExistsResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}"
    restClient.headRequest(endpoint).map { jsResult =>
      ElasticIndexExistsResult(
        throwable = None,
        exists = (jsResult \ "statuscode").asOpt[Int].contains(200)
      )
    }.recover {
      case ex: Throwable =>
        ElasticIndexExistsResult(
          throwable = Some(ex),
          exists = false
        )
    }
  }

  def insertDocument(indexName: String, documentType: String, id: String, document: JsObject): Future[ElasticInsertDocumentResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/$documentType/$id"
    val content: String = Json.stringify(document) + "\n"
    restClient.putJson(endpoint, content, compressed = compressCommunication).map{jsResult =>
      ElasticInsertDocumentResult(jsResult)
    }.recover{
      case ex: Throwable =>
        ElasticInsertDocumentResult(
          throwable = Some(ex),
          _index = indexName,
          _type = documentType,
          _id = id
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
          throwable = Some(ex),
          took = 0,
          errors = true,
          items = List.empty[BulkOperationResult]
        )
    }
  }

  def deleteDocumentByQuery(indexName: String, documentType: String, query: Query): Future[ElasticDeleteByQueryResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/$documentType/_delete_by_query"
    val queryContent: JsValue = DeleteDocumentByQueryWrapper(query)
    restClient.postJson(endpoint, queryContent, compressed = compressCommunication).map{jsResult =>
      ElasticDeleteByQueryResult(jsResult,indexName,documentType)
    }.recover{
      case ex: Throwable =>
        ElasticDeleteByQueryResult(
          throwable = Some(ex),
          _index = indexName,
          _type = documentType,
          total = 0
        )
    }
  }

  def deleteDocument(indexName: String, documentType: String, id: String): Future[ElasticDeleteDocumentResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/$documentType/$id"
    restClient.delete(endpoint, compressCommunication).map { jsResult =>
      ElasticDeleteDocumentResult(
        throwable = None,
        found = (jsResult \ "found").asOpt[Boolean].getOrElse(false)
      )
    }.recover {
      case ex: Throwable =>
        ElasticDeleteDocumentResult(
          throwable = Some(ex),
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
        id = (jsValue \ "_id").asOpt[String].getOrElse(""),
        throwable = None
      )
    } recover {
      case ex: Throwable =>
        ElasticPercolateRegisterResult(
          throwable = Some(ex),
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
        throwable = None,
        found = (jsResult \ "result").asOpt[String].contains("deleted")
      )
    } recover {
      case ex: Throwable =>
        ElasticPercolateUnregisterResult(
          throwable = Some(ex),
          found = false
        )
    }
  }

  def findRegisteredQueries(indexName: String, typeName: String, document: JsValue): Future[ElasticPercolateResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/_search"

    restClient.getJson(endpoint, document, compressCommunication).map { jsResult =>
      ElasticPercolateResult(
        throwable = None,
        matches = extractPercolateResult(jsResult),
        total = (jsResult \ "hits" \ "total").asOpt[Int].getOrElse(0)
      )
    }.recover {
      case ex: Throwable =>
        ElasticPercolateResult(
          throwable = Some(ex),
          matches = List.empty[ElasticPercolateResultMatch],
          total = 0
        )
    }
  }

  private def extractPercolateResult(jsResult: JsValue): List[ElasticPercolateResultMatch] = {
    (jsResult \ "hits" \ "hits").asOpt[JsArray].map { values =>
      values.as[List[JsValue]].map { inner =>
        ElasticPercolateResultMatch(
          score = (inner \ "_score").asOpt[Float].getOrElse(0),
          percolateMatch = (inner \ "_id").asOpt[String].getOrElse("")
        )
      }
    }.getOrElse(List.empty[ElasticPercolateResultMatch])
  }

}
