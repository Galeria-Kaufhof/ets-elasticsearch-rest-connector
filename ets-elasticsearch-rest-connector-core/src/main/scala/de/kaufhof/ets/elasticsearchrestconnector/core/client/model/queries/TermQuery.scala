package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import play.api.libs.json.{JsObject, Json}

case class TermQuery(field: String, value: String, boost: Double = 1.0) extends QueryExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "term" -> Json.obj(
        field -> value
      )
    )
  }
}