package de.kaufhof.ets.elasticsearchrestconnector.core.model.percolate

import de.kaufhof.ets.elasticsearchrestconnector.core.model.QueryListBool
import play.api.libs.json.{JsObject, Json}

object PercolateQueryObject {

  def apply(o: QueryListBool): JsObject = {
    Json.obj(
      "query" -> Json.obj(
        "bool" -> QueryListBool.writes.writes(o)
      )
    )
  }

}
