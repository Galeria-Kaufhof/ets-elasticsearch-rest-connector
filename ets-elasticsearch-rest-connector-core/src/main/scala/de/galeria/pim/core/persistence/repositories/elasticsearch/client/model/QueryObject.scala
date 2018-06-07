package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.aggregations.query.{Aggregation, ChildAggregation}
import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.querySources.{BaseSourceBoolean, QueryBaseSource}
import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queryTypes.Query
import play.api.libs.json.{JsObject, Json, OWrites}

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
  implicit val writes: OWrites[QueryObject] = Json.writes[QueryObject]
}

