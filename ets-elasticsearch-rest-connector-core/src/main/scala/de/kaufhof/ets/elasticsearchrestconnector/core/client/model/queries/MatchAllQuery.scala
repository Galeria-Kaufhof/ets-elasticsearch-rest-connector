package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

case class MatchAllQuery(boost: Int = 1) extends QueryExpression