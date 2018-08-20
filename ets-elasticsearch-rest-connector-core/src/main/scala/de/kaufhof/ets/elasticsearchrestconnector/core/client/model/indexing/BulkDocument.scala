package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.indexing

import play.api.libs.json.JsObject

case class BulkDocument(docHeader: JsObject, optionalDocument: Option[JsObject])
