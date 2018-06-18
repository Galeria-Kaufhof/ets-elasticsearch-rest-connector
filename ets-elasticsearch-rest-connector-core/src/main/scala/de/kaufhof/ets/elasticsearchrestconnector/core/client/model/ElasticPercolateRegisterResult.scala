package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticPercolateRegisterResult(
                                           override val throwable: Option[Throwable],
                                           created: Boolean,
                                           version: Int,
                                           id: String
                                         ) extends ElasticResult
