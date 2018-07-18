package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.template

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.{IndexSettings, Mapping, MappingType}
import org.scalatest.{Matchers, WordSpec}

class IndexTemplateTest extends WordSpec with Matchers {
  "IndexTemplate" should {

    "return a valid json" in {
      val compare: String = """{
                              |  "index_patterns" : [ "*a*", "b", "c*" ],
                              |  "settings" : {
                              |    "analysis" : { },
                              |    "index.mapping.total_fields.limit" : 1000,
                              |    "number_of_shards" : 5,
                              |    "number_of_replicas" : 1,
                              |    "index.max_result_window" : 10000
                              |  },
                              |  "mappings" : {
                              |    "mappingName" : {
                              |      "numeric_detection" : true,
                              |      "dynamic_templates" : [ ],
                              |      "date_detection" : true,
                              |      "properties" : { },
                              |      "dynamic" : "strict"
                              |    }
                              |  }
                              |}""".stripMargin


      val t: IndexTemplate = IndexTemplate(
        indexPatterns = List("*a*", "b", "c*"),
        indexSettings = IndexSettings(),
        mapping = mapping.Mapping(name = "mappingName", dynamic = MappingType.Strict, dateDetection = true, numericDetection = true, dynamicTemplates = List.empty, properties = List.empty)
      )

      t.toString shouldEqual compare

    }
  }
}
