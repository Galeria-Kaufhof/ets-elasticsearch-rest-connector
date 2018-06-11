package de.kaufhof.ets.elasticsearchrestconnector.core.model.hits

case class HitSource(key: String, value: String)
case class Hit(_id: String, _source: Seq[HitSource])

case class ElasticSearchHits(hits: Seq[Hit])