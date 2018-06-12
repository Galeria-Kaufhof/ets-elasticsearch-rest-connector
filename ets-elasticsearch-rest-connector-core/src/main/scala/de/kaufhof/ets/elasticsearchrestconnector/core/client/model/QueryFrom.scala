package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

import play.api.libs.json.{JsNumber, JsValue, Writes}

case class QueryFrom(value: Int = 0)

object QueryFrom {
  implicit val writes: Writes[QueryFrom] = new Writes[QueryFrom] {
    override def writes(o: QueryFrom): JsValue = {
      JsNumber(o.value)
    }
  }
}