package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queries

case class WildCardQuery(field: String, value: String, boost: Double = 1) extends QueryExpression
