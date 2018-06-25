package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsValue, Json}

class JoinMappingPropertyTest extends WordSpec with Matchers {

  "JoinMappingProperty" should {

    "return a valid json with given parameters" in {
      val compare: (String, String) = "propertyName" -> """{
                                                          |  "type" : "join",
                                                          |  "relations" : {
                                                          |    "parent1" : "child1"
                                                          |  }
                                                          |}""".stripMargin

      val o: JoinMappingProperty = JoinMappingProperty("propertyName", "parent1", "child1")
      val result: (String, JsValue) = JoinMappingProperty(o)

      result._1 -> Json.prettyPrint(result._2) shouldEqual compare
    }

  }


}
