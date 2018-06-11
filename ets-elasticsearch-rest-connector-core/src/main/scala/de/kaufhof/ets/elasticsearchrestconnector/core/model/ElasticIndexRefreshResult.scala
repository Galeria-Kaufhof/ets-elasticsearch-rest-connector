package de.kaufhof.ets.elasticsearchrestconnector.core.model

case class ElasticIndexRefreshResult(
                                      override val hasError: Boolean = false,
                                      override val errorMessage: String = "",
                                      refreshed: Boolean
                                    ) extends ElasticResult
