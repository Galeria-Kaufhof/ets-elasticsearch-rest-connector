package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

import play.api.libs.json.JsObject

case class ElasticGetIndexResult(
                                  override val throwable: Option[Throwable],
                                  index: Option[JsObject]
                                ) extends ElasticResult
