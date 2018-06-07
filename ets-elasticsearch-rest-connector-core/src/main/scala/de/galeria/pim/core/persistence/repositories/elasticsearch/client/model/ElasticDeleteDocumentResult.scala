package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

case class ElasticDeleteDocumentResult(
                                        override val hasError: Boolean = false,
                                        override val errorMessage: String = "",
                                        found: Boolean
                                        ) extends ElasticResult
