package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import play.api.libs.json.{JsObject, JsString, JsValue}

case class LongMappingProperty(
                                name: String, nullValue: Option[Long] = None,
                                optionalFields: Option[Seq[MappingProperty]] = None,
                                optionalIndex: Option[String] = None
                              ) extends MappingProperty

object LongMappingProperty{
  import MappingPropertyImplicits._
  def apply(o: LongMappingProperty): (String, JsValue) = {
    o.name -> JsObject(
      Map("type" -> JsString("long"))
        ++ renderOptionalFields(o.optionalFields)
        ++ renderOptionalIndex(o.optionalIndex)
        ++ renderOptionalNullValueForLong(o.nullValue)
    )
  }
}