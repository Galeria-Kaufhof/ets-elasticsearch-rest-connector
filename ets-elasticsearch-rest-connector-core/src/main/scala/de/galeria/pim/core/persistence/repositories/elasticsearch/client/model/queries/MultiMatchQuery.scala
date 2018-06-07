package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queries

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queries.QueryOperator.QueryOperator

case class MultiMatchQuery(
                            fields: List[String],
                            query: String,
                            operator: QueryOperator = QueryOperator.Or
                          ) extends QueryExpression
