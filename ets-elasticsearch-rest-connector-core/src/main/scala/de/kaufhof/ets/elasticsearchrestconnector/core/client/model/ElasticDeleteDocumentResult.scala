package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

case class ElasticDeleteDocumentResult(
                                        override val throwable: Option[Throwable],
                                        found: Boolean
                                        ) extends ElasticResult
