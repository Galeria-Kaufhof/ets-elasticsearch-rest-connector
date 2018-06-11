package de.kaufhof.ets.elasticsearchrestconnector.core.model.mapping

import play.api.libs.json.{JsObject, JsValue}

case class KeywordMappingProperty(name: String) extends MappingProperty

object KeywordMappingProperty{
  def apply(o: KeywordMappingProperty): (String, JsValue) = {
    o.name -> JsObject(Map(TypeMappingProperty(TypeMappingProperty(EnumMappingTypes.Keyword))))
  }
}