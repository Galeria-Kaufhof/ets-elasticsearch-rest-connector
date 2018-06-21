package scala.de.kaufhof.ets.elasticsearchrestconnector.core.connector

import de.kaufhof.ets.elasticsearchrestconnector.core.connector.ElasticSearchUriParser
import org.apache.http.HttpHost
import org.scalatest.{Matchers, WordSpec}

class ElasticSearchUriParserTest extends WordSpec with Matchers {

  "ElasticSearchUriParser" should {

    "parse uri string" in {
      val searchConfig = ElasticSearchUriParser("elasticsearch://elasticsearch.integration.gkh-setu.de:9300/elasticsearch.integration/pim_vertical")

      searchConfig shouldBe defined
      searchConfig.get.indexName shouldEqual "pim_vertical"
      searchConfig.get.hostList shouldBe a[List[_]]
      searchConfig.get.hostList shouldEqual List(HttpHost.create("elasticsearch.integration.gkh-setu.de:9300"))
    }

    "parse uri string with various hosts / ports" in {
      val searchConfig = ElasticSearchUriParser("elasticsearch://server-1.tld:1000,server-2.tld:1001,server-3.tld:1002/cluster/indexName")
      searchConfig shouldBe defined

      searchConfig.get.indexName shouldEqual "indexName"
      searchConfig.get.hostList shouldBe a[List[_]]
      searchConfig.get.hostList should contain allOf(HttpHost.create("server-1.tld:1000"), HttpHost.create("server-2.tld:1001"), HttpHost.create("server-3.tld:1002"))

    }

    "parse uri string with localhost" in {
      val searchConfig = ElasticSearchUriParser("elasticsearch://localhost:9300/prod1/pim")

      searchConfig shouldBe defined
      searchConfig.get.indexName shouldEqual "pim"
      searchConfig.get.hostList shouldBe a[List[_]]
      searchConfig.get.hostList shouldEqual List(HttpHost.create("localhost:9300"))
    }

  }
}