package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.percolate

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.QueryListBool
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
