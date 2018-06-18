package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.MappingValue

case class ElasticMappingReceiveResult(
                                        override val throwable: Option[Throwable],
                                        mappingValues: List[MappingValue] = List.empty[MappingValue]
                               ) extends ElasticResult {

}
