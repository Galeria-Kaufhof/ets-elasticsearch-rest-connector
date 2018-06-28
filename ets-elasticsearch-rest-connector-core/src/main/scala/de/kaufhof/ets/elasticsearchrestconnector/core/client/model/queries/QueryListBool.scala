package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters.FilterExpression
import play.api.libs.json.{JsArray, JsObject, JsValue, Writes}

case class QueryListBool(
                          must: List[QueryExpression] = List.empty[QueryExpression],
                          should: List[QueryExpression] = List.empty[QueryExpression],
                          mustNot: List[QueryExpression] = List.empty[QueryExpression]
                        )

object QueryListBool {
  implicit val writes: Writes[QueryListBool] = new Writes[QueryListBool] {
    override def writes(o: QueryListBool): JsValue = {
      apply(o, List.empty)
    }
  }

  def apply(q: QueryListBool, filters: List[FilterExpression]): JsObject = {
    JsObject(
      (
        generateEntries("must", q.must) ++
          generateEntries("should", q.should) ++
          generateEntries("must_not", q.mustNot) ++
          generateFilters("filter", filters)
        ).toMap
    )
  }


  private def generateFilters(elementName: String, elements: List[FilterExpression]): Option[(String, JsArray)] = {
    if(elements.nonEmpty) {
      Some(elementName -> JsArray(elements.map(FilterExpression.writes.writes)))
    } else {
      None
    }
  }

  private def generateEntries(elementName: String, elements: List[QueryExpression]): Option[(String, JsArray)] = {
    if (elements.nonEmpty) {
      Some(elementName -> JsArray(elements.map(QueryExpression.writes.writes)))
    } else {
      None
    }
  }

}