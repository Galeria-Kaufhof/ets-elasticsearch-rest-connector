package de.kaufhof.ets.elasticsearchrestconnector.core.model.queries

case class TermsQuery(field: String, values: List[String], minimumShouldMatch: Int = 1) extends QueryExpression