package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsValue, Json}

class LongMappingPropertyTest extends WordSpec with Matchers {

  "LongMappingProperty" should {

    "return a valid json with defaults" in {

      val compare: (String, String) = "long1" -> """{
                                                   |  "type" : "long"
                                                   |}""".stripMargin

      val o: LongMappingProperty = LongMappingProperty("long1")

      val testee: (String, JsValue) = LongMappingProperty(o)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare
    }

    "return a valid json with all values filled" in {

      val compare: (String, String) = "long1" -> """{
                                                   |  "type" : "long",
                                                   |  "fields" : {
                                                   |    "long2" : {
                                                   |      "type" : "long"
                                                   |    },
                                                   |    "long3" : {
                                                   |      "type" : "long"
                                                   |    }
                                                   |  },
                                                   |  "index" : true,
                                                   |  "null_value" : 7
                                                   |}""".stripMargin



      val o: LongMappingProperty = LongMappingProperty("long1", nullValue = Some(7), optionalFields = Some(Seq(LongMappingProperty("long2"), LongMappingProperty("long3"))), optionalIndex = Some(true))

      val testee: (String, JsValue) = LongMappingProperty(o)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare
    }


  }


}
