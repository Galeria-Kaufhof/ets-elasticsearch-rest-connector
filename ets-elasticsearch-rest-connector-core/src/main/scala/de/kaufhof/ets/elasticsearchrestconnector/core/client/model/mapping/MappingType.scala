package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

object MappingType extends Enumeration {
  type MappingType = Value

  val Strict: Value = Value(0, "strict")
  val True: Value = Value(1, "true")
  val False: Value = Value(2, "false")


}