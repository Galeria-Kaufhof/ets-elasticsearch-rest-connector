package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.QueryExpression
import play.api.libs.json.{JsObject, Json}

case class QueryFilter(query: QueryExpression) extends FilterExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "query" -> query
    )
  }
}
