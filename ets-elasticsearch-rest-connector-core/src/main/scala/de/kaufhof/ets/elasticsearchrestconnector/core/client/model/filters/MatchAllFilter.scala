package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import play.api.libs.json.{JsObject, Json}

case class MatchAllFilter() extends FilterExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "match_all" -> Json.obj()
    )
  }
}
