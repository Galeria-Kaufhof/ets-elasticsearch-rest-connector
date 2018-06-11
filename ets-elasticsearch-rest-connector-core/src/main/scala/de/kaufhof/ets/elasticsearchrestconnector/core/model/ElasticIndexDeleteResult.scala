package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

case class ElasticIndexDeleteResult(
                                     override val hasError: Boolean = false,
                                     override val errorMessage: String = "",
                                     deleted: Boolean
                                   ) extends ElasticResult
