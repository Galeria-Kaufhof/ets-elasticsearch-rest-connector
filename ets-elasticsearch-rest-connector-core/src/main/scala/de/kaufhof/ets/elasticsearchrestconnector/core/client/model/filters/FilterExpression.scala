package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.filters

import play.api.libs.json._

trait FilterExpression {
  def toJsonObject: JsObject
}

object FilterExpression {
  implicit val writes: Writes[FilterExpression] = new Writes[FilterExpression] {
    override def writes(o: FilterExpression): JsValue = {
      o.toJsonObject
    }
  }
}