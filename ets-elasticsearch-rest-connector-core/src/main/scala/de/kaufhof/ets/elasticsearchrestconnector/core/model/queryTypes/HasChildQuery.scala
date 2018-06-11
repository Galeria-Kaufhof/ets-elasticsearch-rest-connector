package de.kaufhof.ets.elasticsearchrestconnector.core.model.queryTypes

import de.kaufhof.ets.elasticsearchrestconnector.core.model.QueryType
import de.kaufhof.ets.elasticsearchrestconnector.core.model.queryTypes.ScoreMode.ScoreMode

case class HasChildQuery(
                          childType: String,
                          query: QueryType,
                          scoreMode: ScoreMode = ScoreMode.None
                        ) extends Query
