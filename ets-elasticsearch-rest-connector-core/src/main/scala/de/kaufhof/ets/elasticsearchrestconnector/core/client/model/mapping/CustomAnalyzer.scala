package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

case class CustomAnalyzer(
                         name: String,
                         tokenizer: String,
                         filter: List[String]
                         ) extends Analyzer
