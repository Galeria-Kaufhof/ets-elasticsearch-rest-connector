package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queries

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queries.QueryOperator.QueryOperator

case class QueryStringQuery(fields: List[String], query: String, boost: Double = 1.0, defaultOperator: QueryOperator = QueryOperator.Or) extends QueryExpression
