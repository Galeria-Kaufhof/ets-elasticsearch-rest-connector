package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsValue, Json}

class MappingTest extends WordSpec with Matchers {

  "Mapping" should {

    "return a valid json with default" in {
      val compare: (String, String) = "mappingName" -> """{
                                                         |  "numeric_detection" : false,
                                                         |  "dynamic_templates" : [ ],
                                                         |  "date_detection" : false,
                                                         |  "properties" : { },
                                                         |  "dynamic" : "strict"
                                                         |}""".stripMargin


      val o: Mapping = Mapping(
        name = "mappingName",
        dynamic = MappingType.Strict,
        dateDetection = false,
        numericDetection = false,
        dynamicTemplates = List.empty[DynamicTemplate],
        properties = List.empty[MappingProperty]
      )

      val testee: (String, JsValue) = Mapping(o)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare

    }

    "return a valid json with all filled" in {
      val compare: (String, String) = "mappingName" -> """{
                                                         |  "numeric_detection" : false,
                                                         |  "dynamic_templates" : [ {
                                                         |    "dynamicTemplateName1" : {
                                                         |      "mapping" : {
                                                         |        "type" : "dynamicTemplateType1",
                                                         |        "fields" : {
                                                         |          "string1" : {
                                                         |            "type" : "text"
                                                         |          },
                                                         |          "string2" : {
                                                         |            "type" : "text"
                                                         |          }
                                                         |        }
                                                         |      },
                                                         |      "match_mapping_type" : "matchMappingType1"
                                                         |    }
                                                         |  } ],
                                                         |  "date_detection" : false,
                                                         |  "properties" : {
                                                         |    "name1" : {
                                                         |      "type" : "text"
                                                         |    },
                                                         |    "name2" : {
                                                         |      "type" : "text"
                                                         |    }
                                                         |  },
                                                         |  "routing" : {
                                                         |    "required" : true
                                                         |  },
                                                         |  "_parent" : {
                                                         |    "type" : "parentVal"
                                                         |  },
                                                         |  "dynamic" : "strict"
                                                         |}""".stripMargin


      val dynamicTemplate: DynamicTemplate = DynamicTemplate(
        name = "dynamicTemplateName1",
        dynamicTemplateType = "dynamicTemplateType1",
        fields = List(StringMappingProperty("string1"), StringMappingProperty("string2")),
        matchMappingType = "matchMappingType1"
      )

      val o: Mapping = Mapping(
        name = "mappingName",
        dynamic = MappingType.Strict,
        dateDetection = false,
        numericDetection = false,
        dynamicTemplates = List(dynamicTemplate),
        properties = List(StringMappingProperty("name1"), StringMappingProperty("name2")),
        parent = Some("parentVal"),
        routing = Some(true)
      )

      val testee: (String, JsValue) = Mapping(o)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare

    }

  }


}
