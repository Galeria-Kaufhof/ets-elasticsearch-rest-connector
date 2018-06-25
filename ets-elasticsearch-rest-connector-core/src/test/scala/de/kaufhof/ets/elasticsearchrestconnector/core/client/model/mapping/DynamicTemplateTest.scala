package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json

class DynamicTemplateTest extends WordSpec with Matchers {

  "DynamicTemplate" should {

    "return a valid json with default parameters if no values are provided" in {
      val compared: String = """{
                               |  "dynamicTemplateName1" : {
                               |    "mapping" : {
                               |      "type" : "dynamicTemplateType1",
                               |      "fields" : { }
                               |    },
                               |    "match_mapping_type" : "matchMappingType1"
                               |  }
                               |}""".stripMargin

      val dynamicTemplate: DynamicTemplate = DynamicTemplate(
        name = "dynamicTemplateName1",
        dynamicTemplateType = "dynamicTemplateType1",
        fields = List.empty[MappingProperty],
        matchMappingType = "matchMappingType1"
      )

      val testee = DynamicTemplate.writes.writes(dynamicTemplate)

      Json.prettyPrint(testee) shouldEqual compared

    }

  }


}
