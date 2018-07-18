package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.template

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.{IndexSettings, Mapping}
import play.api.libs.json._

case class IndexTemplate(indexPatterns: List[String], indexSettings: IndexSettings, mapping: Mapping){

  def asJsObject: JsObject = Json.toJson(this).as[JsObject]

  override def toString: String = {
    super.toString
    Json.prettyPrint(asJsObject)
  }
}


object IndexTemplate {
  implicit val writes: Writes[IndexTemplate] = new Writes[IndexTemplate] {
    override def writes(o: IndexTemplate): JsValue = {
      Json.obj(
        "index_patterns" -> o.indexPatterns,
        "settings" -> o.indexSettings,
        "mappings" -> JsObject(Map(Mapping(o.mapping)))
      )
    }
  }
}