package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.querySources

import play.api.libs.json.{JsBoolean, JsValue, Json, Writes}

trait QueryBaseSource{
}

object QueryBaseSource {

  implicit val writes: Writes[QueryBaseSource] = new Writes[QueryBaseSource] {
    override def writes(o: QueryBaseSource): JsValue = {
      o match {
        case a: BaseSourceBoolean =>
          JsBoolean(a.value)
        case b: BaseSourceLists =>
          Json.obj(
            "includes" -> b.includes,
            "excludes" -> b.excludes
          )
      }
    }
  }
}
