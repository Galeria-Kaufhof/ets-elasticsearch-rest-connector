package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsValue, Json}

class BooleanMappingPropertyTest extends WordSpec with Matchers {
  "BooleanMappingProperty" should {
    "return a valid json with default parameters" in {
      val compare: (String, String) = "boolMapping1" ->
        """{
          |  "type" : "boolean"
          |}""".stripMargin


      val o: BooleanMappingProperty = BooleanMappingProperty(name = "boolMapping1")

      val testee: (String, JsValue) = BooleanMappingProperty(o)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare

    }

    "return a valid json if all parameters are filled" in {
      val compare: (String, String) = "boolMapping1" -> """{
                                                          |  "type" : "boolean",
                                                          |  "fields" : {
                                                          |    "name1" : {
                                                          |      "type" : "boolean"
                                                          |    },
                                                          |    "name2" : {
                                                          |      "type" : "boolean"
                                                          |    }
                                                          |  },
                                                          |  "index" : true,
                                                          |  "analyzer" : "analyzer1"
                                                          |}""".stripMargin


      val o: BooleanMappingProperty = BooleanMappingProperty(name="boolMapping1", optionalFields = Some(Seq(BooleanMappingProperty("name1"), BooleanMappingProperty("name2"))), optionalAnalyzer = Some("analyzer1"), optionalIndex = Some(true))

      val testee: (String, JsValue) = BooleanMappingProperty(o)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare

    }
  }
}
