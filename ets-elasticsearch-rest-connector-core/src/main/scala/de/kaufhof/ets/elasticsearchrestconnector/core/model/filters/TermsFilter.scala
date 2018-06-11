package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.filters

case class TermsFilter(field: String, values: List[String]) extends FilterExpression
