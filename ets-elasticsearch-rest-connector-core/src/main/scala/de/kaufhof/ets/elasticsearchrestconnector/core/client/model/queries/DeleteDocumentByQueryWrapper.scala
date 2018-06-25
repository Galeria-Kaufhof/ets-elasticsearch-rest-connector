package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes.Query
import play.api.libs.json.{JsObject, Json}

object DeleteDocumentByQueryWrapper {
  def apply(query: Query): JsObject = {
    Json.obj(
      "query" -> Query.writes.writes(query)
    )
  }
}
