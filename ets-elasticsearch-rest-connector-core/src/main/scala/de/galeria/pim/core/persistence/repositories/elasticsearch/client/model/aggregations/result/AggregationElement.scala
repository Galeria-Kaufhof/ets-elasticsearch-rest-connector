package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.aggregations.result

import play.api.libs.json.{Json, Reads}


case class AggregationElement(key: String, doc_count: Long)

object AggregationElement {
  implicit val reads: Reads[AggregationElement] = Json.reads[AggregationElement]
}
