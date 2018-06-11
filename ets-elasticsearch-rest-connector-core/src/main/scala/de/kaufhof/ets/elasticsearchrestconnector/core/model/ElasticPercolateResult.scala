package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

case class ElasticPercolateResult(
                                   override val hasError: Boolean = false,
                                   override val errorMessage: String = "",
                                   matches: List[ElasticPercolateResultMatch],
                                   total: Int
                                 ) extends ElasticResult


case class ElasticPercolateResultMatch(
                                        score: Float,
                                        percolateMatch: String
                                      )