package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model

object IndexKeys {
  final val Raw: String = "raw"
  final val Keyword: String = "keyword"
  final val KeywordLowercase: String = "keyword_lowercase"
  final val WhitespaceLowercase = "whitespace_lowercase"
  final val Analyzed: String = "true"
  final val NotAnalyzed: String = "false"
  final val Whitespace: String = "whitespace"
}
