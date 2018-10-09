package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticPercolateResult(
                                   matches: List[ElasticPercolateResultMatch],
                                   total: Int
                                 )


case class ElasticPercolateResultMatch(
                                        score: Float,
                                        percolateMatch: String
                                      )