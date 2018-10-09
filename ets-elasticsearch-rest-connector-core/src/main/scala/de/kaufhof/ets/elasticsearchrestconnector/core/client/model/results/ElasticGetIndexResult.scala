package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

import play.api.libs.json.JsObject

case class ElasticGetIndexResult(index: Option[JsObject])
