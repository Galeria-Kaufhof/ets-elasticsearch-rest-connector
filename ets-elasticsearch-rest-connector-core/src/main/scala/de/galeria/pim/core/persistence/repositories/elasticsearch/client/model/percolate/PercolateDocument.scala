package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.percolate

import play.api.libs.json.JsObject

case class PercolateDocument(id: String, document: JsObject)
