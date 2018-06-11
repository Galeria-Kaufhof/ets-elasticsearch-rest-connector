package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.mapping

import play.api.libs.json._

case class DynamicTemplate(
                            name: String,
                            index: String,
                            dynamicTemplateType: String,
                            fields: List[MappingProperty],
                            dynamicTemplateMatch: String,
                            matchMappingType: String,
                            pathMatch: String,
                            fieldData: Option[Boolean] = None,
                            ignoreMalformed: Option[Boolean] = None
                          )


object DynamicTemplate {

  implicit val writes: Writes[DynamicTemplate] = new Writes[DynamicTemplate] {
    override def writes(o: DynamicTemplate): JsValue = {
      Json.obj(
        o.name -> Json.obj(
          "mapping" -> JsObject(Seq(
            Some("index" -> JsString(o.index)),
            Some("type" -> JsString(o.dynamicTemplateType)),
            o.ignoreMalformed.map(b => "ignore_malformed" -> JsBoolean(b)),
            o.fieldData.map(b => "fielddata" -> JsBoolean(b)),
            Some("fields" -> JsObject(o.fields.map(MappingProperty(_))))
          ).flatten),
          "match" -> o.dynamicTemplateMatch,
          "match_mapping_type" -> o.matchMappingType,
          "path_match" -> o.pathMatch
        )
      )
    }
  }

}
