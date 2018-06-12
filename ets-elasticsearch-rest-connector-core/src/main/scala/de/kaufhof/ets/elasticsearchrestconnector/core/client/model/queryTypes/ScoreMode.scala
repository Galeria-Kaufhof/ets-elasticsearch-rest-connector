package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.queryTypes

object ScoreMode extends Enumeration {
  type ScoreMode = Value

  val Min: Value = Value(0, "min")
  val Max: Value = Value(1, "max")
  val Sum: Value = Value(2, "sum")
  val Avg: Value = Value(3, "avg")
  val None: Value = Value(4, "none")
}