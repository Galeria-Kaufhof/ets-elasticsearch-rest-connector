package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.QueryType
import play.api.libs.json.{JsObject, Json}

case class StandardQuery(query: QueryType) extends Query {
  override def toJsonObject: JsObject = {
    Json.toJson(query).as[JsObject]
  }
}
