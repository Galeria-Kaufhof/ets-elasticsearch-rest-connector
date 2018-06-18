package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticIndexExistsResult(
                                     override val throwable: Option[Throwable],
                                     exists: Boolean
                                   ) extends ElasticResult
