package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import play.api.libs.json._


case class BoolQuery(
                      should: List[QueryExpression] = Nil,
                      must: List[QueryExpression] = Nil,
                      mustNot: List[QueryExpression] = Nil
                    ) extends QueryExpression {
  override def toJsonObject: JsObject = {
    //BoolQuery(this)
    Json.obj(
      "bool" ->
        JsObject(
          generateEntries("must", must) ++
            generateEntries("should", should) ++
            generateEntries("must_not", mustNot) toMap
        )
    )
  }

  private def generateEntries(elementName: String, elements: List[QueryExpression]): Option[(String, JsArray)] = {
    if (elements.nonEmpty) {
      Some(elementName -> JsArray(elements.map(QueryExpression.writes.writes)))
    } else {
      None
    }
  }
}

//object BoolQuery {
//
//  implicit val writes: Writes[BoolQuery] = new Writes[BoolQuery] {
//    override def writes(o: BoolQuery): JsValue = {
//      apply(o)
//    }
//  }
//
//  def apply(q: BoolQuery): JsObject = {
//    JsObject(
//      generateEntries("must", q.must) ++
//        generateEntries("should", q.should) ++
//        generateEntries("must_not", q.mustNot) toMap
//    )
//  }
//
//  private def generateEntries(elementName: String, elements: List[QueryExpression]): Option[(String, JsArray)] = {
//    if (elements.nonEmpty) {
//      Some(elementName -> JsArray(elements.map(QueryExpression.writes.writes)))
//    } else {
//      None
//    }
//  }
//
//}
