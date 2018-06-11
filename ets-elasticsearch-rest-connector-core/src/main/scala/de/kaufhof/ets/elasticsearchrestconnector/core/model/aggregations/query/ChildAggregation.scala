package de.kaufhof.ets.elasticsearchrestconnector.core.model.aggregations.query


case class ChildAggregation(aggregationName: String, aggregationType: String, aggregationChildType: String, aggregations: List[TermsAggregation]) extends Aggregation