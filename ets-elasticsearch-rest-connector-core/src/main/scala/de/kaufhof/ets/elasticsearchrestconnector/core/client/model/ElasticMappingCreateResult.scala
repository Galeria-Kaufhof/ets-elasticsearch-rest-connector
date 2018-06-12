package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticMappingCreateResult(
                                       override val hasError: Boolean = false,
                                       override val errorMessage: String = "",
                                       created: Boolean
                                     ) extends ElasticResult {

}
