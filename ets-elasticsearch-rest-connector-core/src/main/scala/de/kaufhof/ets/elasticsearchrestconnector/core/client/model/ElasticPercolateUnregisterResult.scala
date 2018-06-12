package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticPercolateUnregisterResult(
                                             override val hasError: Boolean = false,
                                             override val errorMessage: String = "",
                                             found: Boolean
                                           ) extends ElasticResult
