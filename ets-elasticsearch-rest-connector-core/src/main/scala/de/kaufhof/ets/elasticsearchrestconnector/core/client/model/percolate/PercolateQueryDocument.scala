package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.percolate

import play.api.libs.json.{JsValue, Json}

object PercolateQueryDocument {
  def apply(jsDocument: JsValue): JsValue = {
    Json.obj(
      "query" -> Json.obj(
        "percolate" -> Json.obj(
          "field" -> "query",
          "document" -> jsDocument
        )
      )
    )
  }

}
