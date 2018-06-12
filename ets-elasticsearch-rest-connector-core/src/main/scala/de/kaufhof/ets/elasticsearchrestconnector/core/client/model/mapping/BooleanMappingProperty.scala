package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import play.api.libs.json.{JsObject, JsString, JsValue}

case class BooleanMappingProperty(name: String, optionalFields: Option[Seq[MappingProperty]] = None, optionalAnalyzer: Option[String] = None,optionalIndex: Option[String] = None) extends MappingProperty


object BooleanMappingProperty{
  import MappingPropertyImplicits._
  def apply(o: BooleanMappingProperty): (String, JsValue) = {
    o.name -> JsObject(
      Map("type" -> JsString("boolean"))
        ++ renderOptionalFields(o.optionalFields)
        ++ renderOptionalIndex(o.optionalIndex)
    )
  }
}
