package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.aggregations.result.AggregationResults
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.hits.ElasticSearchHits
import de.kaufhof.ets.elasticsearchrestconnector.core.stream.ScrollId

case class ElasticSearchResult(
                                took: Long,
                                hits: ElasticSearchHits,
                                total: Long,
                                aggregations: Option[AggregationResults],
                                scrollIdOpt: Option[ScrollId],
                                override val throwable: Option[Throwable]
                              ) extends ElasticResult