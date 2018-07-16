package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import play.api.libs.json.{JsObject, Json}

case class WildCardQuery(field: String, value: String, boost: Double = 1) extends QueryExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "wildcard" -> Json.obj(
        field -> Json.obj(
          "value" -> value,
          "boost" -> boost
        )
      )
    )
  }
}