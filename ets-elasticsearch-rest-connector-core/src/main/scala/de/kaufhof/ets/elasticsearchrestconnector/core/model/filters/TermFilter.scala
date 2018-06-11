package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.filters

case class TermFilter(field: String, value: String) extends FilterExpression
