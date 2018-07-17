package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import play.api.libs.json._

case class BoolFilter(
                       should: List[FilterExpression] = List.empty[FilterExpression],
                       must: List[FilterExpression] = List.empty[FilterExpression],
                       mustNot: List[FilterExpression] = List.empty[FilterExpression]
                     ) extends FilterExpression {
  override def toJsonObject: JsObject = {
    if (isEmpty) {
      Json.obj()
    } else {
      Json.obj(
        "bool" -> JsObject(
          (
            generateEntries("must", must) ++
              generateEntries("should", should) ++
              generateEntries("must_not", mustNot)
            ) toMap
        )
      )
    }
  }

  private def isEmpty: Boolean = {
    must.isEmpty && should.isEmpty && mustNot.isEmpty
  }

  private def generateEntries(elementName: String, elements: List[FilterExpression]): Option[(String, JsArray)] = {
    if (elements.nonEmpty) {
      Some(elementName -> JsArray(elements.map(FilterExpression.writes.writes)))
    } else {
      None
    }
  }
}