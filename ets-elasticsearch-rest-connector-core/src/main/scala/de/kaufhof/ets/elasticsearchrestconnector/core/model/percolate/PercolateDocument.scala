package de.kaufhof.ets.elasticsearchrestconnector.core.model.percolate

import play.api.libs.json.JsObject

case class PercolateDocument(id: String, document: JsObject)
