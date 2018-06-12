package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticIndexDeleteResult(
                                     override val hasError: Boolean = false,
                                     override val errorMessage: String = "",
                                     deleted: Boolean
                                   ) extends ElasticResult
