package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.QueryOperator.QueryOperator
import play.api.libs.json.{JsObject, Json}

case class MultiMatchQuery(
                            fields: List[String],
                            query: String,
                            operator: QueryOperator = QueryOperator.Or
                          ) extends QueryExpression {
  override def toJsonObject: JsObject = {
    Json.obj(
      "multi_match" -> Json.obj(
        "query" -> query,
        "fields" -> fields,
        "operator" -> operator
      )
    )
  }
}