package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.mapping

case class CustomAnalyzer(
                         name: String,
                         tokenizer: String,
                         filter: List[String]
                         ) extends Analyzer
