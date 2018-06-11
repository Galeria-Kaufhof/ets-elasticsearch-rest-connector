package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queryTypes

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.QueryType
import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queryTypes.ScoreMode.ScoreMode

case class HasChildQuery(
                          childType: String,
                          query: QueryType,
                          scoreMode: ScoreMode = ScoreMode.None
                        ) extends Query
