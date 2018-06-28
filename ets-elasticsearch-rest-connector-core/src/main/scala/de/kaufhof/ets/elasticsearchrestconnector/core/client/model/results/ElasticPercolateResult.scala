package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticPercolateResult(
                                   override val throwable: Option[Throwable],
                                   matches: List[ElasticPercolateResultMatch],
                                   total: Int
                                 ) extends ElasticResult


case class ElasticPercolateResultMatch(
                                        score: Float,
                                        percolateMatch: String
                                      )