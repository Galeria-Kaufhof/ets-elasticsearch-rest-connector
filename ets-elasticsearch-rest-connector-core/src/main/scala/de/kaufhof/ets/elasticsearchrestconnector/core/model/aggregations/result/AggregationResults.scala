package de.kaufhof.ets.elasticsearchrestconnector.core.model.aggregations.result

import de.galeria.pim.core.persistence.repositories.elasticsearch.AggregationResultElement
import play.api.libs.json._

case class AggregationResults(aggregationList: List[AggregationResult])

object AggregationResults {
  implicit val reads: Reads[AggregationResults] = new Reads[AggregationResults] {
    override def reads(json: JsValue): JsResult[AggregationResults] = {
      (json \ "aggregations").toEither match {
        case Right(jsValue: JsValue) =>
          val aggs = jsValue.as[JsObject].value
          val result = aggs.map(renderAggregationResult)
          JsSuccess(AggregationResults(aggregationList = result.toList))
        case Left(error) =>
          JsError(error)
      }
    }
  }

  def renderAggregationResult(tpl: (String, JsValue)): AggregationResult = {
    val content: JsObject = tpl._2.as[JsObject]
    val elements: List[AggregateFieldElement] = renderAggregateFieldElement(content)
    AggregationResult(
      AggregationResultElement(
        key = tpl._1,
        count = (content \ "doc_count").asOpt[Long].getOrElse(elements.headOption.map(_.buckets.size.toLong).getOrElse(0L))
      ),
      fieldElement = elements
    )
  }


  def renderAggregateFieldElement(jso: JsObject): List[AggregateFieldElement] = {
    val elements: Iterable[AggregateFieldElement] = jso.value.flatMap {
      case (key: String, elements: JsObject) =>
        Some(AggregateFieldElement(key, renderAggregateBucketResult(elements)))
      case (key: String, elements: JsArray) if key == "buckets" =>
        Some(AggregateFieldElement(key, renderAggregateBucketResultFromArray(elements)))
      case _ =>
        None
    }
    elements.toList
  }

  def renderAggregateBucketResultFromArray(jsa: JsArray): List[AggregateBucketResult] = {
    jsa.value.map(buildAggregateBucketResult).toList
  }

  def renderAggregateBucketResult(jso: JsObject): List[AggregateBucketResult] = {
    (jso \ "buckets").as[JsArray].value.map(buildAggregateBucketResult).toList
  }


  private def buildAggregateBucketResult(jsValue: JsValue): AggregateBucketResult = {
    AggregateBucketResult(
      key = (jsValue \ "key").as[String],
      doc_count = (jsValue \ "doc_count").as[Long]
    )
  }

}