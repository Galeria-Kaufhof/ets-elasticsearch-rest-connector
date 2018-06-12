package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.indexing

object IndexingAction extends Enumeration {
  type IndexingAction = Value

  final val Index: Value = Value(0, "index")
  final val Create: Value = Value(1, "update")
  final val Delete: Value = Value(2, "delete")
  final val Update: Value = Value(3, "update")

}
