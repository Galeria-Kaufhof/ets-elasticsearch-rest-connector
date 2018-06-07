package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

case class ElasticIndexRefreshResult(
                                      override val hasError: Boolean = false,
                                      override val errorMessage: String = "",
                                      refreshed: Boolean
                                    ) extends ElasticResult
