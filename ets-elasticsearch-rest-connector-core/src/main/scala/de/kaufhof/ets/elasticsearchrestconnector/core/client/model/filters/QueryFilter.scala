package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.QueryExpression

case class QueryFilter(query: QueryExpression) extends FilterExpression
