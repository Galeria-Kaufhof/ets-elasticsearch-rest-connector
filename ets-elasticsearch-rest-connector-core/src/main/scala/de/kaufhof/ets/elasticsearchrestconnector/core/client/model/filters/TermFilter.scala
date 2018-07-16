package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import play.api.libs.json.{JsObject, Json}

case class TermFilter(field: String, value: String) extends FilterExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "term" -> Json.obj(
        field -> value
      )
    )
  }
}
