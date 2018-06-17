package de.kaufhof.ets.elasticsearchrestconnector.core.connector

import org.apache.http.HttpHost

case class ElasticSearchConfiguration(hostList: List[HttpHost],
                                      indexName: String,
                                      clusterName: Option[String]
                                     )