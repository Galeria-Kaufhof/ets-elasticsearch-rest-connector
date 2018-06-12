package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.aggregations.result

import AggregationResultElement

case class AggregationResult(resultElement: AggregationResultElement, fieldElement: List[AggregateFieldElement])