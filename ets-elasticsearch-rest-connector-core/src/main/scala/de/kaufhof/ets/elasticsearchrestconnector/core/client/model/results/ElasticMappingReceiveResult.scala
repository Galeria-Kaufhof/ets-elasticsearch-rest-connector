package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.MappingValue

case class ElasticMappingReceiveResult(mappingValues: List[MappingValue] = List.empty[MappingValue])