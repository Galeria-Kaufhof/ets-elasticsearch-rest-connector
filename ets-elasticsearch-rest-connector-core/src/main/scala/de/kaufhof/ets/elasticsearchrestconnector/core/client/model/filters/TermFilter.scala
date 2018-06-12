package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

case class TermFilter(field: String, value: String) extends FilterExpression
