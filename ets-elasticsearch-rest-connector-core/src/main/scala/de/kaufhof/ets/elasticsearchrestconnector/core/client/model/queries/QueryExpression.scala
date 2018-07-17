package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queries

import play.api.libs.json._

trait QueryExpression{
  def toJsonObject: JsObject
}

object QueryExpression{
  implicit val writes: Writes[QueryExpression] = new Writes[QueryExpression] {
    override def writes(o: QueryExpression): JsValue = {
      o.toJsonObject
    }
  }
}