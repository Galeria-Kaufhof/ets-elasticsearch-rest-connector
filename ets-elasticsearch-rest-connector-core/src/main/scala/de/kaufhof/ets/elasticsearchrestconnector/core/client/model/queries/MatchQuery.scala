package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import play.api.libs.json.{JsObject, Json}

case class MatchQuery(fieldName: String, value: String) extends QueryExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "match" -> Json.obj(
        fieldName -> value
      )
    )
  }
}