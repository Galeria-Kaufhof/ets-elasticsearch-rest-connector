package de.kaufhof.ets.elasticsearchrestconnector.core.connector

import scala.util.matching.Regex

object ElasticSearchUriParser {

  def apply(uri: String, parentRelationName: String, childRelationName: String): ParentChildSearchConfiguration = {
    val nameSpace = "(.*)"
    val clusterNameEntry = "(.*)"
    val hostAddresses = "(.*:[0-9]{1,5}\\,*)"
    val patternUri = new Regex(s"elasticsearch://$hostAddresses/$clusterNameEntry/$nameSpace", "hosts", "clusterName", "indexName")

    (uri match {
      case patternUri(hosts, clusterName, indexName) =>
        val newUri: String = s"elasticsearch://$hosts"
        Some(ParentChildSearchConfiguration(newUri, indexName + "_main", Some(clusterName), parentRelationName, childRelationName))
      case _ => None
    }).get

  }

}
