package de.kaufhof.ets.elasticsearchrestconnector.core.model.aggregations.result

case class AggregateFieldElement(key: String, buckets: List[AggregateBucketResult])