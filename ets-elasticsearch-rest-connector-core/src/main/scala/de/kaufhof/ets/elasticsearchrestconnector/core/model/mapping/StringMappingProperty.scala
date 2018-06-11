package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.mapping

import play.api.libs.json.{JsObject, JsString, JsValue}

case class StringMappingProperty(name: String, optionalFields: Option[Seq[MappingProperty]] = None,
                                 optionalAnalyzer: Option[String] = None,
                                 optionalIndex: Option[String] = None,
                                 optionalFieldData: Option[Boolean] = None
                                ) extends MappingProperty

object StringMappingProperty{
  import MappingPropertyImplicits._
  def apply(o: StringMappingProperty): (String, JsValue) = {
    o.name -> JsObject(
      Map("type" -> JsString("text"))
        ++ renderOptionalFields(o.optionalFields)
        ++ renderOptionalAnalyzer(o.optionalAnalyzer)
        ++ renderOptionalIndex(o.optionalIndex)
        ++ renderOptionalFieldData(o.optionalFieldData)
    )
  }
}