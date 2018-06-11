package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.aggregations.result.AggregationResults
import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.hits.ElasticSearchHits

case class ElasticSearchResult(
                                took: Long,
                                hits: ElasticSearchHits,
                                total: Long,
                                aggregations: Option[AggregationResults],
                                override val hasError: Boolean = false,
                                override val errorMessage: String = ""
                              ) extends ElasticResult