package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticRemoveTemplateResult(
                                        override val throwable: Option[Throwable],
                                        deleted: Boolean
                                      ) extends ElasticResult
