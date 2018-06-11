package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.filters

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queries.QueryExpression

case class QueryFilter(query: QueryExpression) extends FilterExpression
