package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import play.api.libs.json.{JsObject, JsString, JsValue}

case class JoinMappingProperty(name: String, parent: String, child: String) extends MappingProperty

object JoinMappingProperty{
  def apply(j: JoinMappingProperty): (String, JsValue) = {
    j.name -> JsObject(
      Map(
        TypeMappingProperty(TypeMappingProperty(EnumMappingTypes.Join)),
        "relations" -> JsObject(Map(
          j.parent -> JsString(j.child)
        ))
      )
    )
  }
}
