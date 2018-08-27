package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import play.api.libs.json._

case class MappingObject(indexName: String, mapping: Option[Mapping], settings: IndexSettings) {
  override def toString: String = Json.prettyPrint(Json.toJson(this))

  def asJsObject: JsObject = Json.toJson(this).as[JsObject]
}


object MappingObject {

  implicit val writes: Writes[MappingObject] = new Writes[MappingObject] {
    override def writes(mappingObject: MappingObject): JsValue = {
      JsObject(
        (
          generateMapping(mappingObject.mapping) ++
            Some("settings" -> IndexSettings.writes.writes(mappingObject.settings))
          ).toMap
      )
    }

    private def generateMapping(someMapping: Option[Mapping]): Option[(String, JsValue)] = {
      someMapping match {
        case Some(mapping) => Some("mappings" -> JsObject(Seq(Mapping(mapping))))
        case _ => Some("mappings" -> JsObject(Seq.empty))
      }
    }
  }

}
