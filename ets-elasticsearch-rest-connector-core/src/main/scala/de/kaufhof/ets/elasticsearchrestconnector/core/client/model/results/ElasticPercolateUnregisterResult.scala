package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticPercolateUnregisterResult(
                                             override val throwable: Option[Throwable],
                                             found: Boolean
                                           ) extends ElasticResult
