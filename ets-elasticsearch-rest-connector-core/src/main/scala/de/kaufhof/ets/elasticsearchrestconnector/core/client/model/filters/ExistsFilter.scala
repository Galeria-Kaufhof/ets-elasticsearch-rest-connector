package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import play.api.libs.json.{JsObject, Json}

case class ExistsFilter(field: String) extends FilterExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "exists" -> Json.obj(
        "field" -> field
      )
    )
  }
}
