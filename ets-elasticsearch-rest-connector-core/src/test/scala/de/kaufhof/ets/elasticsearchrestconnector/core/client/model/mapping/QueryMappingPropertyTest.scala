package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsValue, Json}

class QueryMappingPropertyTest extends WordSpec with Matchers {

  "QueryMappingProperty" should {

    "return a valid json" in {
      val compare: (String, String) = "query" -> """{
                                                   |  "type" : "percolator"
                                                   |}""".stripMargin

      val o: MappingProperty = QueryMappingProperty(typ = TypeMappingProperty(EnumMappingTypes.Percolator))

      val testee: (String, JsValue) = MappingProperty(o)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare

    }

  }

}
