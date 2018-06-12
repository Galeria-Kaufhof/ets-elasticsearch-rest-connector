package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.aggregations.result

case class AggregateFieldElement(key: String, buckets: List[AggregateBucketResult])