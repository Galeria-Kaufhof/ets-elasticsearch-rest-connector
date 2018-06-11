package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

case class ElasticIndexFlushResult(
                                    override val hasError: Boolean = false,
                                    override val errorMessage: String = "",
                                    flushed: Boolean
                                  ) extends ElasticResult
