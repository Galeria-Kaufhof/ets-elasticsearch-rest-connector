package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.aggregations.query

import play.api.libs.json.{JsObject, JsValue, Json, Writes}

trait Aggregation {

}

object Aggregation {
  def renderAggregationList(l: List[AggregationType]): JsObject = {
    JsObject(
      l.map(a => a.renderAggregationType(a))
    )
  }

  implicit val writes: Writes[Aggregation] = new Writes[Aggregation] {
    override def writes(o: Aggregation): JsValue = {
      o match {
        case c: ChildAggregation =>
          Json.obj(
            c.aggregationName -> Json.obj(
              c.aggregationType -> Json.obj(
                "type" -> c.aggregationChildType
              ),
              "aggregations" -> renderAggregationList(c.aggregations)
            )
          )
        case s: SimpleAggregation =>
          renderAggregationList(s.aggregations)
      }
    }
  }

}