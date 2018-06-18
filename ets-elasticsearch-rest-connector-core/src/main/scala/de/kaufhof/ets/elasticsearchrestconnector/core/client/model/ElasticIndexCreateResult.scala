package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticIndexCreateResult(
                                      override val throwable: Option[Throwable],
                                      created: Boolean
                                     ) extends ElasticResult
