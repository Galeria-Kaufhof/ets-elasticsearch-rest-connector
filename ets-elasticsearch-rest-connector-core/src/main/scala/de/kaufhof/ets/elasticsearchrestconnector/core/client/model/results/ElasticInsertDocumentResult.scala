package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.indexing.ResultShardInfo
import play.api.libs.json._

case class ElasticInsertDocumentResult(_shards: Option[ResultShardInfo] = None,
                                        _index: String,
                                        _type: String,
                                        _id: String,
                                        _version: Option[Long] = None,
                                        _seq_no: Option[Long] = None,
                                        _primary_term: Option[Long] = None,
                                        result: Option[String] = None
                                      )


object ElasticInsertDocumentResult {

  def apply(jsResult: JsValue): ElasticInsertDocumentResult = {
    ElasticInsertDocumentResult(
      _index = (jsResult \ "_index").as[String],
      _id = (jsResult \ "_id").as[String],
      _type = (jsResult \ "_type").as[String],
      _shards = (jsResult \ "_shards").asOpt[ResultShardInfo],
      _version = (jsResult \ "_version").asOpt[Long],
      _seq_no = (jsResult \ "_seq_no").asOpt[Long],
      result = (jsResult \ "result").asOpt[String],
      _primary_term = (jsResult \ "_primary_term").asOpt[Long]
    )
  }
}
