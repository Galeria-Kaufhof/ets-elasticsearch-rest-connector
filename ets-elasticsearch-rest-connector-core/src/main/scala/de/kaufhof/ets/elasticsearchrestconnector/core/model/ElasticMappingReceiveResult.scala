package de.kaufhof.ets.elasticsearchrestconnector.core.model

import de.kaufhof.ets.elasticsearchrestconnector.core.model.mapping.MappingValue

case class ElasticMappingReceiveResult(
                               override val hasError: Boolean = false,
                               override val errorMessage: String = "",
                               mappingValues: List[MappingValue] = List.empty[MappingValue]
                               ) extends ElasticResult {

}
