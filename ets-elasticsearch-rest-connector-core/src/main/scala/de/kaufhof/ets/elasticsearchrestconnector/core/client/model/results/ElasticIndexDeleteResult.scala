package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticIndexDeleteResult(
                                     override val throwable: Option[Throwable],
                                     deleted: Boolean
                                   ) extends ElasticResult
