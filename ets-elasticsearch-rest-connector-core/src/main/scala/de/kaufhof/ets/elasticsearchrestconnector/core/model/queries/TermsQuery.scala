package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queries

case class TermsQuery(field: String, values: List[String], minimumShouldMatch: Int = 1) extends QueryExpression