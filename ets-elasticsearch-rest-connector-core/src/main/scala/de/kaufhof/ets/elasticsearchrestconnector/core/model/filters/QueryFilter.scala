package de.kaufhof.ets.elasticsearchrestconnector.core.model.filters

import de.kaufhof.ets.elasticsearchrestconnector.core.model.queries.QueryExpression

case class QueryFilter(query: QueryExpression) extends FilterExpression
