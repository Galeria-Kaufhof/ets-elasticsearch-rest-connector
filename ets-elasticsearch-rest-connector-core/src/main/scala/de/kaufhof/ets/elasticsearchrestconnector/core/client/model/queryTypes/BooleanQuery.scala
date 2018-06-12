package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters.FilterExpression
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries.{MatchAllQuery, QueryExpression}
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.{QueryListBool, QueryType}
import play.api.libs.json.{JsArray, JsObject, JsValue, Writes}

case class BooleanQuery(query: QueryListBool = QueryListBool(), filter: List[FilterExpression] = Nil) extends QueryType

object BooleanQuery {
  implicit val writes: Writes[BooleanQuery] = new Writes[BooleanQuery] {
    override def writes(query: BooleanQuery): JsValue = {
      val modified: BooleanQuery = if(query.query.must.isEmpty && query.query.mustNot.isEmpty && query.query.should.isEmpty){
        query.copy(query = QueryListBool(must = List(MatchAllQuery())))
      } else {
        query
      }
      createJsObject(modified.query, modified.filter)
    }
  }

  def createJsObject(q: QueryListBool, filters: List[FilterExpression]): JsObject = {
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