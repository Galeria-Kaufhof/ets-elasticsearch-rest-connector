package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.mapping

import play.api.libs.json._

case class MappingObject(indexName: String, mappings: List[Mapping], settings: IndexSettings) {
  override def toString: String = Json.prettyPrint(Json.toJson(this))

  def asJsObject: JsObject = Json.toJson(this).as[JsObject]
}


object MappingObject {

  implicit val writes: Writes[MappingObject] = new Writes[MappingObject] {
    override def writes(mappingObject: MappingObject): JsValue = {
      Json.obj(
        "mappings" -> JsObject(mappingObject.mappings.map(Mapping(_))),
        "settings" -> mappingObject.settings
      )
    }
  }

}
