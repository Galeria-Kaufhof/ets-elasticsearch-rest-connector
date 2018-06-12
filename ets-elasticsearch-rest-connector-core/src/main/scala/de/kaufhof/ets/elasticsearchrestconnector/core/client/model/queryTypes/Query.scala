package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes

import play.api.libs.json.{JsObject, JsValue, Json, Writes}

trait Query{

}

object Query{
  implicit val writes: Writes[Query] = new Writes[Query] {
    override def writes(o: Query): JsValue = {
      o match {
        case h: HasChildQuery =>
          Json.obj("has_child" -> Json.obj(
            "type" -> h.childType,
            "query" -> h.query,
            "score_mode" -> h.scoreMode
          ))
        case s: StandardQuery =>
          Json.toJson(s.query).as[JsObject]
      }
    }
  }
}