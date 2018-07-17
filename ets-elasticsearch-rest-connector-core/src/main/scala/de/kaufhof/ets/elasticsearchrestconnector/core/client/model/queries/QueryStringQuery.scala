package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.QueryOperator.QueryOperator
import play.api.libs.json.{JsObject, Json}

case class QueryStringQuery(fields: List[String], query: String, boost: Double = 1.0, defaultOperator: QueryOperator = QueryOperator.Or) extends QueryExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "query_string" -> Json.obj(
        "fields" -> fields,
        "query" -> query,
        "boost" -> boost,
        "default_operator" -> defaultOperator
      )
    )
  }
}