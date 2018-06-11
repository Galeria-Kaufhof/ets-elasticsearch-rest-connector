package de.kaufhof.ets.elasticsearchrestconnector.core.model

import de.kaufhof.ets.elasticsearchrestconnector.core.model.filters.MatchAllFilter
import de.kaufhof.ets.elasticsearchrestconnector.core.model.queryTypes.BooleanQuery
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