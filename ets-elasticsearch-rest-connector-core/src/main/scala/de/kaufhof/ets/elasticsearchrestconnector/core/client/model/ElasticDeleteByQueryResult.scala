package de.kaufhof.ets.elasticsearchrestconnector.core.client.model

import play.api.libs.json.{JsValue, Json, Reads, Writes}

case class ElasticDeleteByQueryResultRetries(bulk: Long, search: Long)

object ElasticDeleteByQueryResultRetries {
  implicit val reads: Reads[ElasticDeleteByQueryResultRetries] = Json.reads[ElasticDeleteByQueryResultRetries]
  implicit val writes: Writes[ElasticDeleteByQueryResultRetries] = Json.writes[ElasticDeleteByQueryResultRetries]
}


case class ElasticDeleteByQueryResult(
                                       override val throwable: Option[Throwable] = None,
                                       _index: String,
                                       _type: String,
                                       took: Long = 0,
                                       timed_out: Option[Boolean] = None,
                                       deleted: Long = 0,
                                       batches: Long = 0,
                                       version_conflicts: Long = 0,
                                       noops: Long = 0,
                                       retries: Option[ElasticDeleteByQueryResultRetries] = None,
                                       throttled_millis: Long = 0,
                                       requests_per_second: Double = 0,
                                       throttled_until_millis: Long = 0,
                                       total: Long
                                     ) extends ElasticResult

object ElasticDeleteByQueryResult{
  def apply(jsResult: JsValue, index: String, _type: String): ElasticDeleteByQueryResult = {
    ElasticDeleteByQueryResult(
      throwable = None,
      _index = index,
      _type = _type,
      took = (jsResult \ "took").asOpt[Long].getOrElse(9),
      timed_out = (jsResult \ "timed_out").asOpt[Boolean],
      deleted = (jsResult \ "deleted").as[Long],
      batches = (jsResult \ "batches").as[Long],
      version_conflicts = (jsResult \ "version_conflicts").as[Long],
      noops = (jsResult \ "noops").as[Long],
      retries = (jsResult \ "retries").asOpt[ElasticDeleteByQueryResultRetries],
      throttled_millis = (jsResult \ "throttled_millis").as[Long],
      requests_per_second = (jsResult \ "requests_per_second").as[Double],
      throttled_until_millis = (jsResult \ "throttled_until_millis").as[Long],
      total = (jsResult \ "total").as[Long]
    )
  }
}