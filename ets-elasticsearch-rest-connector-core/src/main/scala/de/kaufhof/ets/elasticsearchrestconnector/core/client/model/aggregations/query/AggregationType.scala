package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.aggregations.query

import play.api.libs.json.{JsObject, Json}

trait AggregationType {
  def renderAggregationType(o: AggregationType): (String, JsObject) = {
    o match {
      case t: TermsAggregation =>
        t.name -> Json.obj(
          "terms" -> Json.obj(
            "field" -> t.field,
            "size" -> t.size,
            "min_doc_count" -> t.minimumDocCount
          )
        )
    }
  }
}