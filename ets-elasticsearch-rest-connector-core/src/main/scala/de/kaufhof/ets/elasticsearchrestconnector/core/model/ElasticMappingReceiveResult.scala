package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.mapping.MappingValue

case class ElasticMappingReceiveResult(
                               override val hasError: Boolean = false,
                               override val errorMessage: String = "",
                               mappingValues: List[MappingValue] = List.empty[MappingValue]
                               ) extends ElasticResult {

}
