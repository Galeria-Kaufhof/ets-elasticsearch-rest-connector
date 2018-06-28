package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

trait ElasticResult {
  val throwable: Option[Throwable]
}
