package de.galeria.pim.core.persistence.repositories.elasticsearch.client.services

import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.ElasticSearchResult
import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.aggregations.result.AggregationResults
import de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.hits.{ElasticSearchHits, Hit, HitSource}
import play.api.libs.json._

object JsonToSearchResultConverter {
  private final val _id: String = "_id"
  private final val _source: String = "_source"
  private final val _hits: String = "hits"
  private final val _took: String = "took"
  private final val _total: String = "total"

  def apply(json: JsValue): ElasticSearchResult = {
    val results: Seq[JsValue] = (json \ _hits \ _hits).as[Seq[JsValue]]
    val hitResults: ElasticSearchHits = renderSearchHits(results)

    val maybeAggregations: JsResult[AggregationResults] = Json.fromJson[AggregationResults](json)
    val took: Long = (json \ _took).as[Long]
    val total: Long = (json \ _hits \ _total).as[Long]
    ElasticSearchResult(
      took = took,
      total = total,
      aggregations = maybeAggregations.asOpt,
      hits = hitResults
    )

  }

  private def renderSearchHits(results: Seq[JsValue]): ElasticSearchHits = {
    if (results.nonEmpty) {
      val hits: Seq[Hit] = results.map { jsValue =>
        val jsObject: Option[JsObject] = (jsValue \ _source).asOpt[JsObject]
        val hitSources: Seq[HitSource] = jsObject.map(_.fields.map { tpl =>
          HitSource(tpl._1, tpl._2.as[String])
        }).getOrElse(Seq.empty[HitSource])
        val _idValue: String = (jsValue \ _id).as[String]
        Hit(_idValue, hitSources)
      }
      ElasticSearchHits(hits = hits)

    } else {
      ElasticSearchHits(Seq.empty)
    }
  }
}