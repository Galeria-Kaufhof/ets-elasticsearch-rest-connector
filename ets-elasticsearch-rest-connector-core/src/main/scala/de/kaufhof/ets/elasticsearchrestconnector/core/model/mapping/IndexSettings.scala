package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.mapping

import play.api.libs.json._

case class IndexSettings(analyzer: Option[List[Analyzer]], totalFieldLimit: Int = 1000)

object IndexSettings {

  implicit val writes: Writes[IndexSettings] = new Writes[IndexSettings] {
    override def writes(o: IndexSettings): JsValue = {
      Json.obj(
        "analysis" -> JsObject(
          Seq(
            o.analyzer.map(list => "analyzer" -> JsObject(list.map(Analyzer(_))))
          ).flatten
        ),
        "index.mapping.total_fields.limit" -> JsNumber(o.totalFieldLimit)
      )
    }
  }

}
