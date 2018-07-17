package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import play.api.libs.json.{JsObject, JsString, Json}

case class MatchFilter(fieldName: String, value: String) extends FilterExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "match" -> Json.obj(
        fieldName -> JsString(value)
      )
    )
  }
}
