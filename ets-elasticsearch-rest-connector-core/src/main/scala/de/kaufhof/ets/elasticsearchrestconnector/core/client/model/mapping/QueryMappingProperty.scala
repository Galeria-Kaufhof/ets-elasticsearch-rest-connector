package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import play.api.libs.json.{JsObject, JsValue}

case class QueryMappingProperty(typ: TypeMappingProperty) extends MappingProperty

object QueryMappingProperty{
  def apply(o: QueryMappingProperty): (String, JsValue) = {
    "query" -> JsObject(Map(TypeMappingProperty(o.typ)))
  }
}