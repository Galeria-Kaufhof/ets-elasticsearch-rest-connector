package de.kaufhof.ets.elasticsearchrestconnector.core.model

case class ElasticIndexExistsResult(
                                     override val hasError: Boolean = false,
                                     override val errorMessage: String = "",
                                     exists: Boolean
                                   ) extends ElasticResult
