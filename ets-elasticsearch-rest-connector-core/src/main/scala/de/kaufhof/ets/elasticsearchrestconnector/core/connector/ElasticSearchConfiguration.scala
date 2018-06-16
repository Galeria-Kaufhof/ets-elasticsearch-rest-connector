package de.kaufhof.ets.elasticsearchrestconnector.core.connector

case class ElasticSearchConfiguration(uri: String,
                                      indexName: String,
                                      clusterName: Option[String])