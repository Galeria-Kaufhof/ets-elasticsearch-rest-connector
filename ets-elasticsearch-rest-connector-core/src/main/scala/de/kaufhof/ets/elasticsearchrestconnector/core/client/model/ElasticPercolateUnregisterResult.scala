package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticPercolateUnregisterResult(
                                             override val throwable: Option[Throwable],
                                             found: Boolean
                                           ) extends ElasticResult
