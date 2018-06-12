package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

case class TermsFilter(field: String, values: List[String]) extends FilterExpression
