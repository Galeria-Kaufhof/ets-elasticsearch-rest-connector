package de.kaufhof.ets.elasticsearchrestconnector.core.model.filters

case class TermsFilter(field: String, values: List[String]) extends FilterExpression
