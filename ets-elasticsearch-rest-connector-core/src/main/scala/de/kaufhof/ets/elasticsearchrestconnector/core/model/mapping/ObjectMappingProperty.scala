package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.mapping

import play.api.libs.json.{JsObject, JsValue, Json}

case class ObjectMappingProperty(name: String, properties: List[MappingProperty]) extends MappingProperty

object ObjectMappingProperty{
  def apply(o: ObjectMappingProperty): (String, JsValue) = {
    o.name -> Json.obj(
      "properties" -> JsObject(o.properties.map(MappingProperty(_)))
    )
  }
}