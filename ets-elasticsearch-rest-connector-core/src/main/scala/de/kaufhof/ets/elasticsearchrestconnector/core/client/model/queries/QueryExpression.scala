package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import play.api.libs.json._

trait QueryExpression{}

object QueryExpression{
  implicit val writes: Writes[QueryExpression] = new Writes[QueryExpression] {
    override def writes(o: QueryExpression): JsValue = {
      o match {
        case t: TermQuery =>
          Json.obj(
            "term" -> Json.obj(
              t.field -> t.value
            )
          )
        case t: TermsQuery =>
          Json.obj(
            "terms" -> Json.obj(
              t.field -> t.values
            )
          )
        case r: RangeQuery =>
          Json.obj(
            "range" -> Json.obj(
              r.fieldName -> JsObject(
                Seq(
                  Some("boost" -> JsNumber(r.boost)),
                  r.gt.map {
                    d => "gt" -> JsNumber(d)
                  },
                  r.gte.map {
                    d => "gte" -> JsNumber(d)
                  },
                  r.lt.map {
                    d => "lt" -> JsNumber(d)
                  },
                  r.lte.map {
                    d => "lte" -> JsNumber(d)
                  }).flatten
              )
            )
          )
        case m: MatchAllQuery =>
          Json.obj(
            "match_all" -> Json.obj(
              "boost" -> m.boost
            )
          )
        case m: MultiMatchQuery =>
          Json.obj(
            "multi_match" -> Json.obj(
              "query" -> m.query,
              "fields" -> m.fields,
              "operator" -> m.operator
            )
          )
        case w: WildCardQuery =>
          Json.obj(
            "wildcard" -> Json.obj(
              w.field -> Json.obj(
                "value" -> w.value,
                "boost" -> w.boost
              )
            )
          )
        case q: QueryStringQuery =>
          Json.obj(
            "query_string" -> Json.obj(
              "fields" -> q.fields,
              "query" -> q.query,
              "boost" -> q.boost,
              "default_operator" -> q.defaultOperator
            )
          )
        case b: BoolQuery =>
          Json.obj(
            "bool" -> BoolQuery(b)
          )
        case e: ExistsQuery =>
          Json.obj(
            "exists" -> Json.obj(
              "field" -> e.field
            )
          )
      }
    }
  }
}