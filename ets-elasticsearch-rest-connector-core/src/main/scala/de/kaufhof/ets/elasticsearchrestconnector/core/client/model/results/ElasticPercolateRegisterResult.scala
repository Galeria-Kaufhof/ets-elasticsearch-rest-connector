package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.results

case class ElasticPercolateRegisterResult(created: Boolean,
                                           version: Int,
                                           id: String)
