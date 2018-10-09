package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.indexing.BulkOperationResult
import play.api.libs.json.JsObject

case class ElasticBulkResult(
                                    took: Long,
                                    errors: Boolean,
                                    items: List[BulkOperationResult]
                                  )


object ElasticBulkResult {

  def apply(o: JsObject): ElasticBulkResult = {
    ElasticBulkResult(
      took = (o \ "took").as[Long],
      errors = (o \ "errors").as[Boolean],
      items = (o \ "items").asOpt[List[JsObject]].map(_.flatMap(jso => BulkOperationResult.reads.reads(jso).asOpt)).getOrElse(List.empty)
    )
  }

}