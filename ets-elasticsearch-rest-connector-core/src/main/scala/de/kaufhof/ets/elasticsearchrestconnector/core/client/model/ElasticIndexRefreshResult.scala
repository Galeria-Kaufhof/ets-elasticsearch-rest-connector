package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticIndexRefreshResult(
                                      override val throwable: Option[Throwable],
                                      refreshed: Boolean
                                    ) extends ElasticResult
