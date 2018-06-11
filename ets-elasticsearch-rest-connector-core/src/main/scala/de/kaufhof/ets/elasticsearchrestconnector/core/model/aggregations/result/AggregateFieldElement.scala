package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.aggregations.result

case class AggregateFieldElement(key: String, buckets: List[AggregateBucketResult])