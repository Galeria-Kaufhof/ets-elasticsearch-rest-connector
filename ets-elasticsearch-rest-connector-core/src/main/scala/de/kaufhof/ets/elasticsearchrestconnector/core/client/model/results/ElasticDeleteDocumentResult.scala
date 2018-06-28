package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticDeleteDocumentResult(
                                        override val throwable: Option[Throwable],
                                        found: Boolean
                                        ) extends ElasticResult
