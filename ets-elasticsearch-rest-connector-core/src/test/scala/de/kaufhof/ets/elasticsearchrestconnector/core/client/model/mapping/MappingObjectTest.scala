package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json

class MappingObjectTest extends WordSpec with Matchers {

  "MappingObject" should {
    "return a valid json" in {
      val compare: String = """{
                              |  "mappings" : { },
                              |  "settings" : {
                              |    "analysis" : {
                              |      "analyzer" : {
                              |        "analyzerName" : {
                              |          "type" : "custom",
                              |          "filter" : [ "filter1", "filter2" ],
                              |          "tokenizer" : "tokenizer"
                              |        }
                              |      }
                              |    },
                              |    "index.mapping.total_fields.limit" : 1000,
                              |    "number_of_shards" : 5,
                              |    "number_of_replicas" : 1
                              |  }
                              |}""".stripMargin


      val o: MappingObject = MappingObject("iName", List.empty[Mapping], settings = IndexSettings(analyzer = Some(List(CustomAnalyzer("analyzerName", "tokenizer", List("filter1", "filter2"))))))

      val testee = MappingObject.writes.writes(o)

      Json.prettyPrint(testee) shouldEqual compare
    }
  }
}
