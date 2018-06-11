package de.kaufhof.ets.elasticsearchrestconnector.core.model

import play.api.libs.json.{JsNumber, JsValue, Writes}

case class QuerySize(value: Int = 10)

object QuerySize {
  implicit val writes: Writes[QuerySize] = new Writes[QuerySize] {
    override def writes(o: QuerySize): JsValue = {
      JsNumber(o.value)
    }
  }
}