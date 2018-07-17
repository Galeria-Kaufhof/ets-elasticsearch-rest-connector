package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters
import play.api.libs.json.{JsNumber, JsObject, Json}

case class RangeFilter(gt: Option[Double] = None, gte: Option[Double] = None, lt: Option[Double] = None, lte: Option[Double] = None, fieldName: String) extends FilterExpression {
  override def toJsonObject: JsObject = {
              Json.obj(
                "range" -> Json.obj(
                  fieldName -> JsObject(
                    Seq(
                      gt.map {
                        d => "gt" -> JsNumber(d)
                      },
                      gte.map {
                        d => "gte" -> JsNumber(d)
                      },
                      lt.map {
                        d => "lt" -> JsNumber(d)
                      },
                      lte.map {
                        d => "lte" -> JsNumber(d)
                      }).flatten
                  )
                )
              )
  }
}
