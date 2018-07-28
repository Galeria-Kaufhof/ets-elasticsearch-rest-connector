package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.percolate

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.EnumMappingTypes
import play.api.libs.json.{JsString, JsValue, Json}

object PercolateQueryDocument {
  def apply(jsDocument: JsValue): JsValue = {
    Json.obj(
      "query" -> Json.obj(
        "percolate" -> Json.obj(
          "field" -> "query",
          "document" -> jsDocument,
          "type" -> JsString(EnumMappingTypes.Percolator.toString)
        )
      )
    )
  }

}
