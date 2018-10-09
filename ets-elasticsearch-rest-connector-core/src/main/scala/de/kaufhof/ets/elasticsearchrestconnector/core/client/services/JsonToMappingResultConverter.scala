package de.kaufhof.ets.elasticsearchrestconnector.core.client.services

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.MappingValue
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results.ElasticMappingReceiveResult
import play.api.libs.json.{JsObject, JsValue}

object JsonToMappingResultConverter {

  def apply(json: JsValue): ElasticMappingReceiveResult = {
    val mappingValues: List[MappingValue] = json.as[JsObject].fields.flatMap{fields =>
      (fields._2 \ "mappings").as[JsObject].fields.flatMap{fields =>
        val mappingRoot = (fields._2 \ "properties").as[JsObject].value
        recursiveCalculateMappings(mappingRoot, List.empty)
      }
    } toList

    ElasticMappingReceiveResult(mappingValues = mappingValues)
  }


  def recursiveCalculateMappings(map: collection.Map[String, JsValue], tRes: List[MappingValue]): List[MappingValue] = {
    map.flatMap{tpl =>
      (tpl._2 \ "type").asOpt[String] match {
        case Some(s: String) =>
          tRes :+ MappingValue(tpl._1, List.empty[MappingValue])
        case _ =>
          (tpl._2 \ "properties").asOpt[JsObject] match {
            case Some(v) =>
              tRes :+ MappingValue(tpl._1, recursiveCalculateMappings(v.value, tRes))
            case _ =>
              tRes :+ MappingValue(tpl._1, List.empty[MappingValue])
          }
      }
    } toList
  }
}
