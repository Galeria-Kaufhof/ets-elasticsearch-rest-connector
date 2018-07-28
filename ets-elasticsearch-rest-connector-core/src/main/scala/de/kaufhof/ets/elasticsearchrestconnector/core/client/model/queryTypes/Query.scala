package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes

import play.api.libs.json.{JsObject, JsValue, Json, Writes}

trait Query{
  def toJsonObject: JsObject
}

object Query{
  implicit val writes: Writes[Query] = new Writes[Query] {
    override def writes(o: Query): JsValue = {
      o.toJsonObject
    }
  }
}