package de.kaufhof.ets.elasticsearchrestconnector.core.connector

case class ParentChildSearchConfiguration(uri: String,
                                          indexName: String,
                                          clusterName: Option[String],
                                          typeNameParent: String,
                                          typeNameChild: String)
