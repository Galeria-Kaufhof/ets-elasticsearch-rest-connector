package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.QueryOperator.QueryOperator

case class MultiMatchQuery(
                            fields: List[String],
                            query: String,
                            operator: QueryOperator = QueryOperator.Or
                          ) extends QueryExpression
