package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queries

case class MatchAllQuery(boost: Int = 1) extends QueryExpression