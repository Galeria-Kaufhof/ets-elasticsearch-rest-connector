package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.percolate

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.QueryListBool
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
