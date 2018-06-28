package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes.BooleanQuery
import play.api.libs.json._

trait QueryType {
}


object QueryType {
  implicit val writes: Writes[QueryType] = new Writes[QueryType] {
    override def writes(o: QueryType): JsValue = {
      o match {
        case b: BooleanQuery =>
          Json.obj(
            "bool" -> BooleanQuery.writes.writes(b)
            )
      }
    }
  }
}