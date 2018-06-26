package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.{JsValue, Json}

class ObjectMappingPropertyTest extends WordSpec with Matchers {

  "ObjectMappingProperty" should {
    "return a valid json" in {
      val compare: (String, String) = "objectName" -> """{
                                                        |  "properties" : {
                                                        |    "object2" : {
                                                        |      "properties" : { }
                                                        |    },
                                                        |    "object3" : {
                                                        |      "properties" : { }
                                                        |    }
                                                        |  }
                                                        |}""".stripMargin

      val o: MappingProperty = ObjectMappingProperty(name = "objectName", properties = List(ObjectMappingProperty("object2"), ObjectMappingProperty("object3")))

      val testee: (String, JsValue) = MappingProperty(o)

      testee._1 -> Json.prettyPrint(testee._2) shouldEqual compare
    }

  }

}
