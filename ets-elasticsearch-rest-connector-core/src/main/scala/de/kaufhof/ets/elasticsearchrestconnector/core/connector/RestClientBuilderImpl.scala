package de.kaufhof.ets.elasticsearchrestconnector.core.connector

import org.apache.http.HttpHost
import org.elasticsearch.client.{RestClient, RestClientBuilder}

object RestClientBuilderImpl {

  def build(hosts: List[HttpHost]): RestClientBuilder = {
    RestClient.builder(hosts: _*)
  }
}
