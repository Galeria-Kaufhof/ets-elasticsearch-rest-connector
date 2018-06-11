package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.aggregations.query


case class ChildAggregation(aggregationName: String, aggregationType: String, aggregationChildType: String, aggregations: List[TermsAggregation]) extends Aggregation