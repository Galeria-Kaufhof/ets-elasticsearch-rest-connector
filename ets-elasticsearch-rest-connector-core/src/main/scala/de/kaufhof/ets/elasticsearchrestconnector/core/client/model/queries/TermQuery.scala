package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

case class TermQuery(field: String, value: String, boost: Double = 1.0) extends QueryExpression