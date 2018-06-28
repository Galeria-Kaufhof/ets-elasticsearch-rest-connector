package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.aggregations.result.AggregationResults
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.hits.ElasticSearchHits

case class ElasticSearchResult(
                                took: Long,
                                hits: ElasticSearchHits,
                                total: Long,
                                aggregations: Option[AggregationResults],
                                override val throwable: Option[Throwable]
                              ) extends ElasticResult