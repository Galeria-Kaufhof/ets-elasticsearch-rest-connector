package de.kaufhof.ets.elasticsearchrestconnector.core.connector

import org.apache.http.HttpHost
import org.scalatest.{Matchers, WordSpec}

class ElasticsearchClientUriTest extends WordSpec with Matchers {

  "ElasticsearchClientUri" should {

    "parse a valid uri" in {
      val result = ElasticsearchClientUri("elasticsearch://server-1.tld:1000,server-2.tld:1001,server-3.tld:1002")
      result.hosts should contain allOf("server-1.tld" -> 1000, "server-2.tld" -> 1001, "server-3.tld" -> 1002)
    }

  }

}
