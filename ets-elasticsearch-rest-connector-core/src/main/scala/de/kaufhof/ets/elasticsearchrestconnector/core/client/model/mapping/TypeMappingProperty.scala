package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.EnumMappingTypes.EnumMappingType
import play.api.libs.json.{JsString, JsValue}

object EnumMappingTypes extends Enumeration{
  type EnumMappingType = Value

  val Keyword: Value = Value("keyword")
  val Percolator: Value = Value("percolator")
  val Join: Value = Value("join")

}


case class TypeMappingProperty(typ: EnumMappingType) extends MappingProperty

object TypeMappingProperty {
  def apply(t: TypeMappingProperty): (String, JsValue) = {
    "type" -> JsString(t.typ.toString)
  }
}