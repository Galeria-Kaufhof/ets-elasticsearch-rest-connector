package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

trait ElasticResult {
  val hasError: Boolean
  val errorMessage: String
}
