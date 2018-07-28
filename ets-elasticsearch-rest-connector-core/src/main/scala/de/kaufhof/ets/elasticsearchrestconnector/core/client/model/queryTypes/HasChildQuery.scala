package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.QueryType
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes.ScoreMode.ScoreMode
import play.api.libs.json.{JsObject, Json}

case class HasChildQuery(
                          childType: String,
                          query: QueryType,
                          scoreMode: ScoreMode = ScoreMode.None
                        ) extends Query {
  override def toJsonObject: JsObject = {
    Json.obj("has_child" -> Json.obj(
      "type" -> childType,
      "query" -> query,
      "score_mode" -> scoreMode
    ))
  }
}
