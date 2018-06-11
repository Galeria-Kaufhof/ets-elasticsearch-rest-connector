package de.kaufhof.ets.elasticsearchrestconnector.core.model.filters

case class TermFilter(field: String, value: String) extends FilterExpression
