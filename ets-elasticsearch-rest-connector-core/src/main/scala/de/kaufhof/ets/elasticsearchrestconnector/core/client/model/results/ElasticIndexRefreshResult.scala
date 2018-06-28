package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticIndexRefreshResult(
                                      override val throwable: Option[Throwable],
                                      refreshed: Boolean
                                    ) extends ElasticResult
