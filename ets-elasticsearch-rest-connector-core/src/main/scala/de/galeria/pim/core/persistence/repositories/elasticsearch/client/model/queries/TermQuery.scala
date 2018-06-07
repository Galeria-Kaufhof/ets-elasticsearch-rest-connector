package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.queries

import play.api.libs.json.{Json, Writes}

case class TermQuery(field: String, value: String, boost: Double = 1.0) extends QueryExpression


object TermQuery {

  implicit def writes: Writes[TermQuery] = Json.writes[TermQuery]
}