package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.template.IndexTemplate
import play.api.libs.json.JsObject

case class ElasticGetTemplateResult(template: Option[JsObject])
