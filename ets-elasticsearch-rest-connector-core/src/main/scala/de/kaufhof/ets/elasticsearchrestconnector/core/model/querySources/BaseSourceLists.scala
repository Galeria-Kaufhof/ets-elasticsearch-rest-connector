package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.querySources

case class BaseSourceLists(
                            includes: List[String] = Nil,
                            excludes: List[String] = Nil
                          ) extends QueryBaseSource
