package de.kaufhof.ets.elasticsearchrestconnector.core.model.aggregations.query

case class TermsAggregation(name: String, field: String, size: Long, minimumDocCount: Int = 1) extends AggregationType