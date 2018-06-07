package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

case class ElasticIndexExistsResult(
                                     override val hasError: Boolean = false,
                                     override val errorMessage: String = "",
                                     exists: Boolean
                                   ) extends ElasticResult
