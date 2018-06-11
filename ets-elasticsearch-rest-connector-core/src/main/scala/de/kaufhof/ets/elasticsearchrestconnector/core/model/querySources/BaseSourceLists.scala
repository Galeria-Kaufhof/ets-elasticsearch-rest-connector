package de.kaufhof.ets.elasticsearchrestconnector.core.model.querySources

case class BaseSourceLists(
                            includes: List[String] = Nil,
                            excludes: List[String] = Nil
                          ) extends QueryBaseSource
