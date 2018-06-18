package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

trait ElasticResult {
  val throwable: Option[Throwable]
}
