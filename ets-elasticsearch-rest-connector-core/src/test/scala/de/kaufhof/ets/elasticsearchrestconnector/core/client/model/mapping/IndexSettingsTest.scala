package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import org.scalatest.{Matchers, WordSpec}
import play.api.libs.json.Json

class IndexSettingsTest extends WordSpec with Matchers {

  "IndexSettingsTest" should {

    "return a valid json with defaults if no values are provided" in {
      val compared: String = """{
                       |  "analysis" : { },
                       |  "index.mapping.total_fields.limit" : 1000,
                       |  "number_of_shards" : 5,
                       |  "number_of_replicas" : 1,
                       |  "index.max_result_window" : 10000
                       |}""".stripMargin

      val testee = IndexSettings.writes.writes(IndexSettings())

      Json.prettyPrint(testee) shouldEqual compared
    }

    "return a valid json with given parameters" in {
      val compared: String = """{
                               |  "analysis" : {
                               |    "analyzer" : {
                               |      "custom" : {
                               |        "type" : "custom",
                               |        "filter" : [ "element1", "element2" ],
                               |        "tokenizer" : "tokenizer1"
                               |      }
                               |    }
                               |  },
                               |  "index.mapping.total_fields.limit" : 1,
                               |  "number_of_shards" : 10,
                               |  "number_of_replicas" : 100,
                               |  "index.max_result_window" : 100
                               |}""".stripMargin

      val settings: IndexSettings = IndexSettings(analyzer = Some(List(CustomAnalyzer("custom", "tokenizer1", List("element1", "element2")))), totalFieldLimit = 1, numberOfShards = 10, numberOfReplicas = 100, indexMaxResultWindow = 100)

      val testee = IndexSettings.writes.writes(settings)
      Json.prettyPrint(testee) shouldEqual compared

    }

  }

}
