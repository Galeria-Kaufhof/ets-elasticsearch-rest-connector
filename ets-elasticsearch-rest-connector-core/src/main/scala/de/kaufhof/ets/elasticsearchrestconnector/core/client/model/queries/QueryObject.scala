package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.aggregations.query.Aggregation
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.querySources.{BaseSourceBoolean, QueryBaseSource}
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes.Query
import play.api.libs.json._

case class QueryObject(
                        from: QueryFrom = QueryFrom(),
                        size: QuerySize = QuerySize(),
                        _source: QueryBaseSource = BaseSourceBoolean(),
                        aggregations: Option[Aggregation] = None,
                        sort: Option[List[Sort]] = None,
                        query: Query
                      ) {
  override def toString: String = Json.prettyPrint(Json.toJson(this))

  def asJsObject: JsObject = Json.toJson(this).as[JsObject]

}

object QueryObject {
implicit val writes: OWrites[QueryObject] = new OWrites[QueryObject] {
  override def writes(o: QueryObject): JsObject = {
    JsObject(
      Seq(
        Some("from" -> JsNumber(o.from.value)),
        Some("size" -> JsNumber(o.size.value)),
        Some("_source" -> QueryBaseSource.writes.writes(o._source)),
        o.aggregations.map(aggregation => "aggregations" -> Aggregation.writes.writes(aggregation)),
        o.sort.map(so => "sort" -> JsArray(so.map(s =>Sort.writes.writes(s)))),
        Some("query" -> Query.writes.writes(o.query))
      ).flatten
    )
  }
}
}

