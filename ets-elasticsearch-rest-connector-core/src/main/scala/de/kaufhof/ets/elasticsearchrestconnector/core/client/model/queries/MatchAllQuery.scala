package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import play.api.libs.json.{JsObject, Json}

case class MatchAllQuery(boost: Int = 1) extends QueryExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "match_all" -> Json.obj(
        "boost" -> boost
      )
    )
  }
}