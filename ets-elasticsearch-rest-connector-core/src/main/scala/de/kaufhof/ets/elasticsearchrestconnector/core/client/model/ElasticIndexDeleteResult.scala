package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticIndexDeleteResult(
                                     override val throwable: Option[Throwable],
                                     deleted: Boolean
                                   ) extends ElasticResult
