package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsValue, Json}

class CustomAnalyzerTest extends WordSpec with Matchers {

  "CustomAnalyzer" should {
    "return a valid json with given parameters" in {
      val compare: (String, String) = "ca" -> """{
                                                |  "type" : "custom",
                                                |  "filter" : [ "element1", "element2", "element3" ],
                                                |  "tokenizer" : "token1"
                                                |}""".stripMargin

      val c: Analyzer = CustomAnalyzer("ca", "token1", List("element1", "element2", "element3"))
      val testee: (String, JsValue) = Analyzer(c)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare
    }

  }

}
