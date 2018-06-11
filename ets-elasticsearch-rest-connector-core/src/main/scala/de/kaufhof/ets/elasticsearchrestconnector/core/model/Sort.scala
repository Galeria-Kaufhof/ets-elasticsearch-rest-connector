package de.kaufhof.ets.elasticsearchrestconnector.core.model

import de.kaufhof.ets.elasticsearchrestconnector.core.model.SortMode.SortMode
import de.kaufhof.ets.elasticsearchrestconnector.core.model.SortOrder.SortOrder
import play.api.libs.json.{JsValue, Json, OFormat, Writes}

case class Sort(fieldName: String, order: SortOrder = SortOrder.Ascending, sortMode: SortMode = SortMode.Avg)

object Sort {
  implicit val writes: Writes[Sort] = new Writes[Sort] {
    override def writes(o: Sort): JsValue = {
      Json.obj(
        o.fieldName -> Json.obj(
          "order" -> o.order,
          "mode" -> o.sortMode
        )
      )
    }
  }
}

object SortMode extends Enumeration {
  type SortMode = Value

  val Min: Value = Value(0, "min")
  val Max: Value = Value(1, "max")
  val Sum: Value = Value(2, "sum")
  val Avg: Value = Value(3, "avg")
}

object SortOrder extends Enumeration {
  type SortOrder = Value

  val Ascending: Value = Value(0, "asc")
  val Descending: Value = Value(1, "desc")

}
