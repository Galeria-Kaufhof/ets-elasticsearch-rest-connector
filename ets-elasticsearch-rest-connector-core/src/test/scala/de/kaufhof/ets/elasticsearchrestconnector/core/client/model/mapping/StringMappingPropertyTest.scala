package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsValue, Json}

class StringMappingPropertyTest extends WordSpec with Matchers {

  "StringMappingProperty" should {

    "return a valid json with defaults" in {
      val compare: (String, String) = "string1" -> """{
                                                     |  "type" : "text"
                                                     |}""".stripMargin

      val o: MappingProperty = StringMappingProperty(
        name = "string1"
      )

      val testee: (String, JsValue) = MappingProperty(o)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare
    }

    "return a valid json with all values filled" in {
      val compare: (String, String) = "string1" -> """{
                                                     |  "analyzer" : "analyzer1",
                                                     |  "fields" : {
                                                     |    "long1" : {
                                                     |      "type" : "long"
                                                     |    },
                                                     |    "long2" : {
                                                     |      "type" : "long"
                                                     |    }
                                                     |  },
                                                     |  "type" : "text",
                                                     |  "fielddata" : true,
                                                     |  "index" : true
                                                     |}""".stripMargin

      val o: MappingProperty = StringMappingProperty(
        name = "string1",
        optionalFields = Some(Seq(LongMappingProperty("long1"), LongMappingProperty("long2"))),
        optionalAnalyzer = Some("analyzer1"),
        optionalIndex = Some(true),
        optionalFieldData = Some(true)
      )

      val testee: (String, JsValue) = MappingProperty(o)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare
    }

  }

}
