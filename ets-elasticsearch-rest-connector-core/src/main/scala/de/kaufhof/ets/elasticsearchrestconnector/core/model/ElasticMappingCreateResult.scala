package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

case class ElasticMappingCreateResult(
                                       override val hasError: Boolean = false,
                                       override val errorMessage: String = "",
                                       created: Boolean
                                     ) extends ElasticResult {

}
