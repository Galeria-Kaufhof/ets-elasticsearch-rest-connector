package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.indexing.BulkInsertResultItem
import play.api.libs.json.JsObject

case class ElasticBulkInsertResult(
                                    override val throwable: Option[Throwable],
                                    took: Long,
                                    errors: Boolean,
                                    items: List[BulkInsertResultItem]
                                  ) extends ElasticResult


object ElasticBulkInsertResult {

  def apply(o: JsObject): ElasticBulkInsertResult = {
    ElasticBulkInsertResult(
      throwable = None,
      took = (o \ "took").as[Long],
      errors = (o \ "errors").as[Boolean],
      items = (o \ "items").as[List[JsObject]].map(jso => BulkInsertResultItem(jso))
    )
  }

}