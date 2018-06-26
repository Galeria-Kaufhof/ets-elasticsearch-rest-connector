package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import play.api.libs.json.{JsObject, JsValue, Json}

case class ObjectMappingProperty(name: String, properties: List[MappingProperty] = Nil) extends MappingProperty

object ObjectMappingProperty{
  def apply(o: ObjectMappingProperty): (String, JsValue) = {
    o.name -> Json.obj(
      "properties" -> JsObject(o.properties.map(MappingProperty(_)))
    )
  }
}