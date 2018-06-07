package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.indexing

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.indexing.IndexingAction.IndexingAction
import play.api.libs.json.{JsNumber, JsObject, JsString, Json}

case class DocHeader(action: IndexingAction, index: String, typ: String, id: String, parent: Option[String], routing: Option[String])

object DocHeader {
  def apply(docHeader: DocHeader): JsObject = {
    Json.obj(
      docHeader.action.toString -> JsObject(
        Seq(
          Some("_index" -> JsString(docHeader.index)),
          Some("_type" -> JsString(docHeader.typ)),
          Some("_id" -> JsString(docHeader.id)),
          docHeader.routing.map { routing =>
            "routing" -> JsString(routing)
          }
        ).flatten
      )
    )
  }
}
