package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.aggregations.query

case class SimpleAggregation(aggregations: List[TermsAggregation]) extends Aggregation
