package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticPercolateRegisterResult(
                                           override val hasError: Boolean = false,
                                           override val errorMessage: String = "",
                                           created: Boolean,
                                           version: Int,
                                           id: String
                                         ) extends ElasticResult
