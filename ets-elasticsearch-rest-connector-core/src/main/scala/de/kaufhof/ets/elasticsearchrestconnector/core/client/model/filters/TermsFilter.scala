package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import play.api.libs.json.{JsObject, Json}

case class TermsFilter(field: String, values: List[String]) extends FilterExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "terms" -> Json.obj(
        field -> values
      )
    )
  }
}
