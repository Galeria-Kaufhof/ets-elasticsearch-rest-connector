package de.kaufhof.ets.elasticsearchrestconnector.core.model.queries

import de.kaufhof.ets.elasticsearchrestconnector.core.model.queries.QueryOperator.QueryOperator

case class QueryStringQuery(fields: List[String], query: String, boost: Double = 1.0, defaultOperator: QueryOperator = QueryOperator.Or) extends QueryExpression
