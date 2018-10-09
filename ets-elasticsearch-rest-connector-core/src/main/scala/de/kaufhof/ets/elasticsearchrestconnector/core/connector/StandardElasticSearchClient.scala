package de.kaufhof.ets.elasticsearchrestconnector.core.connector

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.hits.ElasticSearchHits
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.indexing.BulkDocument
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.MappingObject
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.percolate.PercolateDocument
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.{DeleteDocumentByQueryWrapper, QueryObject}
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes.Query
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results._
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.template.IndexTemplate
import de.kaufhof.ets.elasticsearchrestconnector.core.client.services.JsonToMappingResultConverter
import de.kaufhof.ets.elasticsearchrestconnector.core.stream.ScrollApiRequest
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

  def addTemplate(templateName: String, template: IndexTemplate): Future[ElasticCreateTemplateResult] = {
    val endpoint: String = s"/_template/$templateName"
    restClient.putJson(endpoint, template.asJsObject, compressCommunication).map { jsResult =>
      ElasticCreateTemplateResult(acknowledged = (jsResult \ "acknowledged").asOpt[Boolean].getOrElse(false))
    }
  }

  def getTemplate(templateName: String): Future[ElasticGetTemplateResult] = {
    val endpoint: String = s"/_template/$templateName"
    restClient.getJson(endpoint, compressCommunication).map { jsResult =>
      ElasticGetTemplateResult(template = Some(jsResult.as[JsObject]))
    }
  }

  def templateExits(templateName: String): Future[ElasticTemplateExistsResult] = {
    val endpoint: String = s"/_template/$templateName"
    restClient.headRequest(endpoint).map{jsResult =>
      ElasticTemplateExistsResult(exists = (jsResult \ "statuscode").asOpt[Int].contains(200))
    }
  }

  def removeTemplate(templateName: String): Future[ElasticRemoveTemplateResult] = {
    val endpoint: String = s"/_template/$templateName"
    restClient.delete(endpoint, compressCommunication).map { jsResult =>
      ElasticRemoveTemplateResult(deleted = (jsResult \ "acknowledged").asOpt[Boolean].getOrElse(false))
    }
  }

  def search(indexName: String, query: QueryObject, queryParams: Seq[(String, String)]): Future[ElasticSearchResult] = {
    val endpoint: String = s"/${urlEncodePathEntity(indexName)}/_search"
    restClient.postJson(endpoint, query.asJsObject, compressCommunication, queryParams:_*).map(JsonToSearchResultConverter(_))
  }

  def continuedScrollSearch(parameter: ScrollApiRequest): Future[ElasticSearchResult] = {
    val endpoint: String = "/_search/scroll"
    restClient.postJson(endpoint, parameter.toJson, compressCommunication).map(JsonToSearchResultConverter(_))
  }

  def getMapping(indexName: String): Future[ElasticMappingReceiveResult] = {
    val endpoint: String = s"/${urlEncodePathEntity(indexName)}/_mapping"
    restClient.getJson(endpoint, compressCommunication).map(JsonToMappingResultConverter.apply)
  }

  private def urlEncodePathEntity(indexName: String): String = java.net.URLEncoder.encode(indexName, "utf-8")

  def getIndex(indexName: String): Future[ElasticGetIndexResult] = {
    val endpoint: String = s"/$indexName"
    restClient.getJson(endpoint, compressCommunication).map{jsResult =>
      ElasticGetIndexResult(index = jsResult.asOpt[JsObject])
    }
  }


  def createIndex(mappingObject: MappingObject): Future[ElasticIndexCreateResult] = {
    createIndex(mappingObject.indexName, mappingObject.asJsObject)
  }

  def createIndex(indexName: String, mappingAsJson: JsObject): Future[ElasticIndexCreateResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}"
    restClient.putJson(endpoint, mappingAsJson, compressCommunication).map { jsResult =>
      ElasticIndexCreateResult(created = (jsResult \ "acknowledged").asOpt[Boolean].getOrElse(false))
    }
  }

  def createEmptyIndex(indexName: String): Future[ElasticIndexCreateResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}"
    restClient.putJson(endpoint, "", compressCommunication).map{jsResult =>
      ElasticIndexCreateResult(created = (jsResult \ "acknowledged").asOpt[Boolean].getOrElse(false))
    }
  }

  def refreshIndex(indexName: String): Future[ElasticIndexRefreshResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/_refresh"
    restClient.postJson(endpoint, emptyBody, compressCommunication).map { jsResult =>
      val f: Option[Int] = (jsResult \\ "failed").headOption.flatMap(_.asOpt[Int])
      ElasticIndexRefreshResult(refreshed = f.contains(0))
    }
  }

  def flushIndex(indexName: String): Future[ElasticIndexFlushResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/_flush"
    restClient.postJson(endpoint, emptyBody, compressCommunication).map { jsResult =>
      val f = (jsResult \\ "failed").headOption.flatMap(_.asOpt[Int])
      ElasticIndexFlushResult(flushed = f.contains(0))
    }
  }

  def deleteIndex(indexName: String): Future[ElasticIndexDeleteResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}"
    restClient.delete(endpoint, compressCommunication).map { jsResult =>
      ElasticIndexDeleteResult(deleted = (jsResult \ "acknowledged").asOpt[Boolean].getOrElse(false))
    }
  }

  def indexExists(indexName: String): Future[ElasticIndexExistsResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}"
    restClient.headRequest(endpoint).map { jsResult =>
      ElasticIndexExistsResult(exists = (jsResult \ "statuscode").asOpt[Int].contains(200))
    }
  }

  def insertDocument(indexName: String, id: String, document: JsObject): Future[ElasticInsertDocumentResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/$id"
    val content: String = Json.stringify(document) + "\n"
    restClient.putJson(endpoint, content, compressed = compressCommunication).map { jsResult =>
      ElasticInsertDocumentResult(jsResult)
    }
  }

  def bulkOperateDocuments(documents: List[BulkDocument]): Future[ElasticBulkResult] = {
    val endpoint: String = s"/_bulk"
    val bodyContent: String = documents.map { indexedDocument =>
      indexedDocument.optionalDocument match {
        case Some(document) => Json.stringify(indexedDocument.docHeader) + "\n" + Json.stringify(document)
        case _ => Json.stringify(indexedDocument.docHeader)
      }
    } mkString "\n"

    restClient.putJson(endpoint, bodyContent + "\n", compressed = compressCommunication).map { jsResult =>
      ElasticBulkResult(jsResult.as[JsObject])
    }
  }

  def deleteDocumentByQuery(indexName: String, query: Query): Future[ElasticDeleteByQueryResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/_delete_by_query"
    val queryContent: JsValue = DeleteDocumentByQueryWrapper(query)
    restClient.postJson(endpoint, queryContent, compressed = compressCommunication).map[ElasticDeleteByQueryResult] { jsResult =>
      ElasticDeleteByQueryResult(jsResult, indexName)
    }
  }

  def deleteDocument(indexName: String, id: String, documentType: String, queryParameters: Seq[(String, String)]): Future[ElasticDeleteDocumentResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/$documentType/$id"
    restClient.delete(endpoint, compressCommunication, queryParameters:_*).map { jsResult =>
      ElasticDeleteDocumentResult(
        found = (jsResult \ "found").asOpt[Boolean].getOrElse(false)
      )
    }
  }

  def registerPercolatorQuery(indexName: String, typeName: String, percolateDocument: PercolateDocument): Future[ElasticPercolateRegisterResult] = {
    val endpoint: String = s"/${urlEncodePathEntity(indexName)}/$typeName/${urlEncodePathEntity(percolateDocument.id)}"
    restClient.postJson(endpoint, percolateDocument.document, compressCommunication).map { jsValue =>
      ElasticPercolateRegisterResult(
        created = (jsValue \ "result").asOpt[String].exists("created" ==),
        version = (jsValue \ "_version").asOpt[Int].getOrElse(0),
        id = (jsValue \ "_id").asOpt[String].getOrElse("")
      )
    }
  }

  def unregisterPercolatorQuery(indexName: String, typeName: String, id: String): Future[ElasticPercolateUnregisterResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/$typeName/${urlEncodePathEntity(id)}"
    restClient.delete(endpoint, compressCommunication).map { jsResult =>
      ElasticPercolateUnregisterResult(found = (jsResult \ "result").asOpt[String].contains("deleted"))
    }
  }

  def findRegisteredQueries(indexName: String, document: JsValue): Future[ElasticPercolateResult] = {
    val endpoint: String = s"${urlEncodePathEntity(indexName)}/_search"

    restClient.getJson(endpoint, document, compressCommunication).map { jsResult =>
      ElasticPercolateResult(
        matches = extractPercolateResult(jsResult),
        total = (jsResult \ "hits" \ "total").asOpt[Int].getOrElse(0)
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
