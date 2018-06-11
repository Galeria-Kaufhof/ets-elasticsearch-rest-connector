package de.kaufhof.ets.elasticsearchrestconnector.core.model.queries

case class MatchAllQuery(boost: Int = 1) extends QueryExpression