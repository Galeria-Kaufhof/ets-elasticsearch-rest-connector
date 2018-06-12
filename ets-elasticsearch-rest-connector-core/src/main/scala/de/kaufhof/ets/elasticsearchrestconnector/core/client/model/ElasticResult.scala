package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

trait ElasticResult {
  val hasError: Boolean
  val errorMessage: String
}
