package de.kaufhof.ets.elasticsearchrestconnector.core.stream

import play.api.libs.json.{JsObject, JsString, Json}

case class ScrollApiRequest(scrollId: ScrollId, scrollRequestValidationTime: TokenValidationTime) {
  def toJson: JsObject = {
    Json.obj(
      "scroll" -> JsString(scrollRequestValidationTime.toString),
      "scroll_id" -> JsString(scrollId.value)
    )
  }
}
