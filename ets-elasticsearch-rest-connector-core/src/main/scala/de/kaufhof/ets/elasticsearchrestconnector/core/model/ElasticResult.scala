package de.kaufhof.ets.elasticsearchrestconnector.core.model

trait ElasticResult {
  val hasError: Boolean
  val errorMessage: String
}
