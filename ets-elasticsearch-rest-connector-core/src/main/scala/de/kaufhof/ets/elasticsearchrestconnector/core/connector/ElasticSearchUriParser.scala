package de.kaufhof.ets.elasticsearchrestconnector.core.connector

import org.apache.http.HttpHost

import scala.util.matching.Regex

object ElasticSearchUriParser {

  def apply(uri: String): Option[ElasticSearchConfiguration] = {
    val nameSpace = "(.*)"
    val clusterNameEntry = "(.*)"
    val hostAddresses = "(.*:[0-9]{1,5}\\,*)"
    val patternUri = new Regex(s"elasticsearch://$hostAddresses/$clusterNameEntry/$nameSpace", "hosts", "clusterName", "indexName")

    uri match {
      case patternUri(hosts, clusterName, indexName) =>
        val newUri: String = s"elasticsearch://$hosts"

        val restUri: ElasticsearchClientUri = ElasticsearchClientUri(newUri)
        val hostList: List[HttpHost]  = restUri.hosts.map {
          case (hostName: String, port: Int) => new HttpHost(hostName, port)
        }

        Some(ElasticSearchConfiguration(hostList, indexName, Some(clusterName)))
      case _ => None
    }

  }

}
