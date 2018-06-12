package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import play.api.libs.json._

trait FilterExpression {

}

object FilterExpression {
  implicit val writes: Writes[FilterExpression] = new Writes[FilterExpression] {
    override def writes(o: FilterExpression): JsValue = {
      o match {
        case t: TermFilter =>
          Json.obj(
            "term" -> Json.obj(
              t.field -> t.value
            )
          )
        case t: TermsFilter =>
          Json.obj(
            "terms" -> Json.obj(
              t.field -> t.values
            )
          )
        case r: RangeFilter =>
          Json.obj(
            "range" -> Json.obj(
              r.fieldName -> JsObject(
                Seq(
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
        case e: ExistsFilter =>
          Json.obj(
            "exists" -> Json.obj(
              "field" -> e.field
            )
          )
        case m: MatchAllFilter =>
          Json.obj(
            "match_all" -> Json.obj()
          )
        case q: QueryFilter =>
          Json.obj(
            "query" -> q.query
          )
        case b: BoolFilter =>
          if (getSizes(b) == 0) {
            Json.obj()
          } else {
            Json.obj(
              "bool" -> BoolFilter(b)
            )
          }
      }
    }
  }


  private def getSizes(b: BoolFilter): Int = {
    b.must.size + b.should.size + b.mustNot.size
  }
}