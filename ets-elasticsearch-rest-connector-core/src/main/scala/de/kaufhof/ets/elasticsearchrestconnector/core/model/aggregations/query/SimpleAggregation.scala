package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.aggregations.query

case class SimpleAggregation(aggregations: List[TermsAggregation]) extends Aggregation
