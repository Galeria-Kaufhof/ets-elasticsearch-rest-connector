package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queryTypes

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.QueryType

case class StandardQuery(query: QueryType) extends Query
