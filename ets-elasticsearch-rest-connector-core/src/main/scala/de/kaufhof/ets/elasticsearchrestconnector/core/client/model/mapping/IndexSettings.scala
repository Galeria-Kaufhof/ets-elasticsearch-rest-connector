package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import play.api.libs.json._

case class IndexSettings(analyzer: Option[List[Analyzer]] = None, totalFieldLimit: Int = 1000, numberOfShards: Int = 5, numberOfReplicas: Int = 1)

object IndexSettings {

  implicit val writes: Writes[IndexSettings] = new Writes[IndexSettings] {
    override def writes(o: IndexSettings): JsValue = {
      Json.obj(
        "analysis" -> JsObject(
          Seq(
            o.analyzer.map(list => "analyzer" -> JsObject(list.map(Analyzer(_))))
          ).flatten
        ),
        "index.mapping.total_fields.limit" -> JsNumber(o.totalFieldLimit),
        "number_of_shards" -> JsNumber(o.numberOfShards),
        "number_of_replicas" -> JsNumber(o.numberOfReplicas)
      )
    }
  }

}
