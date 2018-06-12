package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import play.api.libs.json.{JsArray, JsObject, JsValue, Writes}

case class BoolFilter(
                     should: List[FilterExpression] = List.empty[FilterExpression],
                     must: List[FilterExpression] = List.empty[FilterExpression],
                     mustNot: List[FilterExpression] = List.empty[FilterExpression]
                     ) extends FilterExpression


object BoolFilter {

  implicit val writes: Writes[BoolFilter] = new Writes[BoolFilter] {
    override def writes(o: BoolFilter): JsValue = {
      apply(o)
    }
  }

  def apply(q: BoolFilter): JsObject = {
    JsObject(
      (
        generateEntries("must", q.must) ++
        generateEntries("should", q.should) ++
        generateEntries("must_not", q.mustNot)
      ) toMap
    )
  }

  private def generateEntries(elementName: String, elements: List[FilterExpression]): Option[(String, JsArray)] = {
    if(elements.nonEmpty) {
      Some(elementName -> JsArray(elements.map(FilterExpression.writes.writes)))
    } else {
      None
    }
  }
}