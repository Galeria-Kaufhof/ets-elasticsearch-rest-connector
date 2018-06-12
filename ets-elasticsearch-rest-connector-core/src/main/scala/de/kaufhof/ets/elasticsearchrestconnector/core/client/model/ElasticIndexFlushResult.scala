package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticIndexFlushResult(
                                    override val hasError: Boolean = false,
                                    override val errorMessage: String = "",
                                    flushed: Boolean
                                  ) extends ElasticResult
