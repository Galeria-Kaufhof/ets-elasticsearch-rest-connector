package de.kaufhof.ets.elasticsearchrestconnector.core.model.queryTypes

import de.kaufhof.ets.elasticsearchrestconnector.core.model.QueryType

case class StandardQuery(query: QueryType) extends Query
