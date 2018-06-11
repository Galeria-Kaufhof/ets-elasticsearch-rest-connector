package de.kaufhof.ets.elasticsearchrestconnector.core.model.aggregations.result

import de.galeria.pim.core.persistence.repositories.elasticsearch.AggregationResultElement

case class AggregationResult(resultElement: AggregationResultElement, fieldElement: List[AggregateFieldElement])