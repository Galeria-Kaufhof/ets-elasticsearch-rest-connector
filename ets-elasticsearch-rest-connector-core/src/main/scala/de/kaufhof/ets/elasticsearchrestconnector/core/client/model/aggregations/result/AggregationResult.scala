package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.aggregations.result

case class AggregationResult(resultElement: AggregationResultElement, fieldElement: List[AggregateFieldElement])