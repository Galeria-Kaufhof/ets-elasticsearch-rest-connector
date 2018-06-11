package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.mapping

import play.api.libs.json.{JsValue, Json}

trait Analyzer

object Analyzer {

  def apply(o: Analyzer): (String, JsValue) = {
    o match {
      case c: CustomAnalyzer =>
        c.name -> Json.obj(
          "type" -> "custom",
          "filter" -> c.filter,
          "tokenizer" -> c.tokenizer
        )
    }
  }

}
