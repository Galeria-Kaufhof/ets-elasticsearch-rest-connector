package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import play.api.libs.json._

case class DynamicTemplate(
                            name: String,
                            index: Option[String] = None,
                            dynamicTemplateType: String,
                            fields: List[MappingProperty],
                            dynamicTemplateMatch: Option[String] = None,
                            matchMappingType: String,
                            pathMatch: Option[String] = None,
                            fieldData: Option[Boolean] = None,
                            ignoreMalformed: Option[Boolean] = None
                          )


object DynamicTemplate {

  implicit val writes: Writes[DynamicTemplate] = new Writes[DynamicTemplate] {
    override def writes(o: DynamicTemplate): JsValue = {
      Json.obj(
        o.name -> JsObject(Seq(
          Some("mapping" -> JsObject(Seq(
            o.index.map(s => "index" -> JsString(s)),
            Some("type" -> JsString(o.dynamicTemplateType)),
            o.ignoreMalformed.map(b => "ignore_malformed" -> JsBoolean(b)),
            o.fieldData.map(b => "fielddata" -> JsBoolean(b)),
            Some("fields" -> JsObject(o.fields.map(MappingProperty(_))))
          ).flatten)),
          o.dynamicTemplateMatch.map(b => "match" -> JsString(b)),
          Some("match_mapping_type" -> JsString(o.matchMappingType)),
          o.pathMatch.map(b => "path_match" -> JsString(b))
        ).flatten)
      )
    }
  }

}
