package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.indexing

import play.api.libs.json.JsObject

case class IndexedDocument(docHeader: JsObject, document: JsObject)
