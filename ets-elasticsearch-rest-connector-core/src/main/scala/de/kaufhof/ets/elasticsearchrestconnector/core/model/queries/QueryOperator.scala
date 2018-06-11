package de.kaufhof.ets.elasticsearchrestconnector.core.model.queries

object QueryOperator extends Enumeration {
  type QueryOperator = Value

  val And: Value = Value(0, "and")
  val Or: Value = Value(1, "or")

}