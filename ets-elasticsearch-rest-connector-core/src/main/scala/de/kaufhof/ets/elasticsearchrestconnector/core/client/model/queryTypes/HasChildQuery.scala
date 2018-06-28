package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.QueryType
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes.ScoreMode.ScoreMode

case class HasChildQuery(
                          childType: String,
                          query: QueryType,
                          scoreMode: ScoreMode = ScoreMode.None
                        ) extends Query
