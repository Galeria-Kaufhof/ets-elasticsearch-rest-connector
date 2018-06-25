package de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping

import play.api.libs.json._

import scala.util.Try

object MappingPropertyImplicits {
  implicit def renderOptionalFields(maybeSeq: Option[Seq[MappingProperty]]): Option[(String, JsObject)] = {
    maybeSeq.map { fields =>
      "fields" -> JsObject(fields.map(MappingProperty(_)))
    }
  }

  implicit def renderOptionalAnalyzer(maybeAnalyzer: Option[String]): Option[(String, JsValue)] = {
    maybeAnalyzer.map { analyzer =>
      "analyzer" -> JsString(analyzer)
    }
  }

  implicit def renderOptionalFieldData(maybeFieldData: Option[Boolean]): Option[(String, JsValue)] = {
    maybeFieldData.map{fieldData =>
      "fielddata" -> JsBoolean(fieldData)
    }
  }

  implicit def renderOptionalIndex(maybeIndex: Option[Boolean]): Option[(String, JsValue)] = {
    maybeIndex.map { index =>
      "index" -> JsBoolean(index)
    }
  }

  implicit def renderOptionalNullValueForLong(maybeNull: Option[Long]): Option[(String, JsValue)] = {
    maybeNull.map { nullValue =>
      "null_value" -> JsNumber(nullValue)
    }
  }
}