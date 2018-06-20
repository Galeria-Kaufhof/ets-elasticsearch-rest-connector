package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.indexing

import play.api.libs.json._

import scala.util.{Failure, Success, Try}

case class OperationResult(
                            bulkType: String,
                            _index: String,
                            _type: String,
                            _id: String,
                            _version: Option[Long],
                            result: Option[String],
                            _shards: Option[ResultShardInfo],
                            status: Long,
                            _seq_no: Option[Long],
                            _primary_term: Option[Long],
                            error: Option[BulkOperationError],
                            hasError: Boolean
                          )

case class BulkOperationError(
                               typ: String,
                               reason: String
                             )
object BulkOperationError {
  implicit val reads: Reads[BulkOperationError] = new Reads[BulkOperationError] {
    override def reads(json: JsValue): JsResult[BulkOperationError] = {
      JsSuccess(
        BulkOperationError(
          typ = (json \ "type").as[String],
          reason = (json \ "reason").as[String]
        )
      )
    }
  }
}


case class ResultShardInfo(
                            total: Long,
                            successful: Long,
                            failed: Long
                          )

object ResultShardInfo {
  implicit val reads: Reads[ResultShardInfo] = new Reads[ResultShardInfo] {
    override def reads(json: JsValue): JsResult[ResultShardInfo] = {
      JsSuccess(
        ResultShardInfo(
          total = (json \ "total").as[Long],
          successful = (json \ "successful").as[Long],
          failed = (json \ "failed").as[Long]
        )
      )
    }
  }
}

case class BulkOperationResult(results: Seq[OperationResult])

object BulkOperationResult {
  implicit val reads: Reads[BulkOperationResult] = new Reads[BulkOperationResult] {
    override def reads(json: JsValue): JsResult[BulkOperationResult] = {
      val outerFields = json.as[JsObject].fields
      val results1: Seq[OperationResult] = outerFields.flatMap(generateOperationResult)
      JsSuccess(
        BulkOperationResult(results = results1)
      )
    }

    def generateOperationResult(tpl: (String, JsValue)): Option[OperationResult] = {
      Try(
        OperationResult(
          bulkType = tpl._1,
          _index = (tpl._2 \ "_index").as[String],
          _type = (tpl._2 \ "_type").as[String],
          _id = (tpl._2 \ "_id").as[String],
          _version = (tpl._2 \ "_version").asOpt[Long],
          result = (tpl._2 \ "result").asOpt[String],
          _shards = (tpl._2 \ "_shards").asOpt[ResultShardInfo],
          _seq_no = (tpl._2 \ "_seq_no").asOpt[Long],
          _primary_term = (tpl._2 \ "_primary_term").asOpt[Long],
          status = (tpl._2 \ "status").as[Long],
          error = (tpl._2 \ "error").asOpt[BulkOperationError],
          hasError = (tpl._2 \ "error").asOpt[BulkOperationError].isDefined
        )
      ) match {
        case Success(s) => Some(s)
        case Failure(th) => None
      }
    }

  }
}