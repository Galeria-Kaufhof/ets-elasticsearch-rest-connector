package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsValue, Json}

class KeywordMappingPropertyTest extends WordSpec with Matchers {

  "KeywordMappingProperty" should {

    "return a valid json" in {
      val compare: (String, String) = "kwp" -> """{
                                                 |  "type" : "keyword"
                                                 |}""".stripMargin


      val o: KeywordMappingProperty = KeywordMappingProperty(name = "kwp")

      val testee: (String, JsValue) = KeywordMappingProperty(o)


      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare


    }

  }


}
