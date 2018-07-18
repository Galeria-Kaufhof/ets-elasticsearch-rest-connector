package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.template.IndexTemplate
import play.api.libs.json.JsObject

case class ElasticGetTemplateResult(
                                     override val throwable: Option[Throwable] = None,
                                     template: Option[JsObject]
                                   ) extends ElasticResult
