package de.kaufhof.ets.elasticsearchrestconnector.core.model.indexing

import play.api.libs.json.JsObject

case class IndexedDocument(docHeader: JsObject, document: JsObject)
