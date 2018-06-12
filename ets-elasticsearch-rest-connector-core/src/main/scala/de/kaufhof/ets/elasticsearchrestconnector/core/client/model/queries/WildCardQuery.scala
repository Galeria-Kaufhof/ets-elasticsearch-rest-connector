package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

case class WildCardQuery(field: String, value: String, boost: Double = 1) extends QueryExpression
