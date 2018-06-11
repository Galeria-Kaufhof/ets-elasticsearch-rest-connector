package de.kaufhof.ets.elasticsearchrestconnector.core.model.aggregations.query

case class SimpleAggregation(aggregations: List[TermsAggregation]) extends Aggregation
