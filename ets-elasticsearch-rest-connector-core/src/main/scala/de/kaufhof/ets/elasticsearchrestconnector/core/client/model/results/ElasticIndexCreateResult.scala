package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticIndexCreateResult(
                                      override val throwable: Option[Throwable],
                                      created: Boolean
                                     ) extends ElasticResult
