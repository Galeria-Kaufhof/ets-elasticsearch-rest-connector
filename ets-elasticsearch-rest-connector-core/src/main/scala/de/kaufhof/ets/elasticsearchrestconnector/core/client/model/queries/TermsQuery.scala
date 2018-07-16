package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import play.api.libs.json.{JsObject, Json}

case class TermsQuery(field: String, values: List[String], minimumShouldMatch: Int = 1) extends QueryExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "terms" -> Json.obj(
        field -> values
      )
    )
  }
}