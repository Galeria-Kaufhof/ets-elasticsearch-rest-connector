package de.kaufhof.ets.elasticsearchrestconnector.core.model

import de.kaufhof.ets.elasticsearchrestconnector.core.model.aggregations.result.AggregationResults
import de.kaufhof.ets.elasticsearchrestconnector.core.model.hits.ElasticSearchHits

case class ElasticSearchResult(
                                took: Long,
                                hits: ElasticSearchHits,
                                total: Long,
                                aggregations: Option[AggregationResults],
                                override val hasError: Boolean = false,
                                override val errorMessage: String = ""
                              ) extends ElasticResult