package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticCreateTemplateResult (
                                         override val throwable: Option[Throwable],
                                         acknowledged: Boolean
                                       ) extends ElasticResult