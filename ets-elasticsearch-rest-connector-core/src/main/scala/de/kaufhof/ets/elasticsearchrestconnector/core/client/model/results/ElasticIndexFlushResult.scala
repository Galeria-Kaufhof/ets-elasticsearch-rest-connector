package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticIndexFlushResult(
                                    override val throwable: Option[Throwable],
                                    flushed: Boolean
                                  ) extends ElasticResult
