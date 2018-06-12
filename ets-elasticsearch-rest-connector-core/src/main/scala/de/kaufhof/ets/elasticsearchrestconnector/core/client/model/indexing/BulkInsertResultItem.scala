package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.indexing

import play.api.libs.json.{JsObject, JsValue}

case class BulkInsertResultItem(
                                 index: String,
                                 typ: String,
                                 id: String,
                                 version: Long,
                                 status: Long,
                                 error: Option[String]
                               )

object BulkInsertResultItem {
  def apply(o: JsObject): BulkInsertResultItem = {
    val inner: collection.Map[String, JsValue] = (o \ "index").as[JsObject].value
    BulkInsertResultItem(
      index = inner("_index").as[String],
      typ = inner("_type").as[String],
      id = inner("_id").as[String],
      version = inner("_version").as[Long],
      status = inner("status").as[Long],
      error = inner.get("error").map(_.as[String])
    )
  }
}