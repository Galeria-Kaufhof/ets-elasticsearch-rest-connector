package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import play.api.libs.json.{JsObject, Json}

case class ExistsQuery(field: String) extends QueryExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "exists" -> Json.obj(
        "field" -> field
      )
    )
  }
}
