package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticTemplateExistsResult(
                                        override val throwable: Option[Throwable],
                                        exists: Boolean
                                      ) extends ElasticResult
