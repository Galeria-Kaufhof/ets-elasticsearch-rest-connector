package de.kaufhof.ets.elasticsearchrestconnector.core

import java.io.InputStream

import com.typesafe.scalalogging.LazyLogging
import org.apache.http.entity.{ByteArrayEntity, ContentType, StringEntity}
import org.apache.http.message.BasicHeader
import org.apache.http.{Header, HttpEntity, HttpHeaders}
import org.elasticsearch.client.{Response, ResponseException, ResponseListener, RestClient}
import play.api.libs.json._

import scala.concurrent.{Future, Promise}

object RichRestClient {

  implicit class RichRestClient(restClient: RestClient) extends RichClient(restClient)

}

class RichClient(restClient: RestClient) extends LazyLogging {

  private final val compressionMode: String = "gzip"

  def headRequest(endpoint: String): Future[JsValue] = {
    val p = Promise[JsValue]()
    restClient.performRequestAsync(
      "HEAD",
      endpoint,
      new ResponseListener {
        override def onFailure(exception: Exception): Unit = exception match {
          case responseException: ResponseException if responseException.getResponse.getStatusLine.getStatusCode == 404 =>
            // TODO Parse exception message? 404 is not always REST resource not found, Akka HTTP returns 404 when param is missing?
            //logger.debug("Treating 404 as success w/o result " + responseException.getMessage)
            logger.error(s"treating a http-404 as success without result ${responseException.getMessage}", responseException)
            p.success(JsNull)
            ()
          case _ =>
            logger.error(s"error while executing a GET to search", exception)
            p.failure(exception)
            ()
        }

        override def onSuccess(response: Response): Unit = {
          val result: _root_.play.api.libs.json.JsObject = JsObject(Map("exists" -> JsBoolean(response.getStatusLine.getStatusCode == 200)))
          p.success(result)
          ()
        }
      }
    )
    p.future
  }

  def getJson(endpoint: String, compressed: Boolean, queryParams: (String, String)*): Future[JsValue] = {
    import collection.JavaConverters._
    val params = Map(queryParams: _*).asJava

    val p = Promise[JsValue]()
    restClient.performRequestAsync(
      "GET",
      endpoint,
      params,
      new ResponseListener {
        override def onSuccess(response: Response): Unit = {
          if (response.getEntity.getContentLength > 0) {
            val json = getJsonFromResult(response)
            p.success(json)
          } else {
            p.success(JsNull)
          }
          ()
        }

        override def onFailure(exception: Exception): Unit = exception match {
          case responseException: ResponseException if responseException.getResponse.getStatusLine.getStatusCode == 404 =>
            // TODO Parse exception message? 404 is not always REST resource not found, Akka HTTP returns 404 when param is missing?
            //logger.debug("Treating 404 as success w/o result " + responseException.getMessage)
            logger.error(s"treating a http-404 as success without result ${responseException.getMessage}", responseException)
            p.success(JsNull)
            ()
          case _ =>
            logger.error(s"error while executing a GET to search", exception)
            p.failure(exception)
            ()
        }
      },
      getHeaders(compressed): _*
    )
    p.future
  }

  def getJson(endpoint: String, body: JsValue, compressed: Boolean, queryParams: (String, String)*): Future[JsValue] = {
    import collection.JavaConverters._
    val params = Map(queryParams: _*).asJava

    val entity: HttpEntity = if (compressed) {
      new ByteArrayEntity(Gzip.compress(Json.stringify(body)), ContentType.APPLICATION_JSON)
    } else {
      new StringEntity(Json.stringify(body), ContentType.APPLICATION_JSON)
    }

    val p = Promise[JsValue]()
    restClient.performRequestAsync(
      "GET",
      endpoint,
      params,
      entity,
      new ResponseListener {
        override def onSuccess(response: Response): Unit = {
          if (response.getEntity.getContentLength > 0) {
            val json = getJsonFromResult(response)
            p.success(json)
          } else {
            p.success(JsNull)
          }
          ()
        }

        override def onFailure(exception: Exception): Unit = exception match {
          case responseException: ResponseException if responseException.getResponse.getStatusLine.getStatusCode == 404 =>
            // TODO Parse exception message? 404 is not always REST resource not found, Akka HTTP returns 404 when param is missing?
            //logger.debug("Treating 404 as success w/o result " + responseException.getMessage)
            logger.error(s"treating a http-404 as success without result ${responseException.getMessage}", responseException)
            p.success(JsNull)
            ()
          case _ =>
            logger.error(s"error while executing a GET to search", exception)
            p.failure(exception)
            ()
        }
      },
      getHeaders(compressed): _*
    )
    p.future
  }

  private def getJsonFromResult(response: Response): JsValue = {

    val compressed: Boolean = response.getHeaders.find(_.getName == HttpHeaders.CONTENT_ENCODING).exists(_.getValue == compressionMode)
    val maybeStream: Option[InputStream] = if (compressed) {
      Gzip.decompress(response.getEntity.getContent)
    } else {
      Some(response.getEntity.getContent)
    }
    maybeStream.map(Json.parse).getOrElse(JsNull)
  }

  private def getHeaders(compressed: Boolean): Seq[Header] = {
    if (compressed) {
      Seq(
        new BasicHeader(HttpHeaders.CONTENT_ENCODING, compressionMode)
      )
    } else {
      Seq.empty[Header]
    }
  }

  def putJson(endpoint: String, body: JsValue, compressed: Boolean, queryParams: (String, String)*): Future[JsValue] = {
    import collection.JavaConverters._
    val params = Map(queryParams: _*).asJava
    val entity: HttpEntity = if (compressed) {
      new ByteArrayEntity(Gzip.compress(Json.stringify(body)), ContentType.APPLICATION_JSON)
      //new StringEntity(Json.stringify(body), ContentType.APPLICATION_JSON)
    } else {
      new StringEntity(Json.stringify(body), ContentType.APPLICATION_JSON)
    }
    val p = Promise[JsValue]()
    restClient.performRequestAsync(
      "PUT",
      endpoint,
      params,
      entity,
      new ResponseListener {
        override def onSuccess(response: Response): Unit = {
          val json: JsValue = getJsonFromResult(response)
          p.success(json)
          ()
        }
        override def onFailure(exception: Exception): Unit = {
          logger.error(s"error while executing a PUT to search", exception)
          p.failure(exception)
          ()
        }
      },
      getHeaders(compressed): _*
    )
    p.future
  }

  def postJson(endpoint: String, body: JsValue, compressed: Boolean, queryParams: (String, String)*): Future[JsValue] = {
    import collection.JavaConverters._
    val params = Map(queryParams: _*).asJava
    val entity: HttpEntity = if (compressed) {
      new ByteArrayEntity(Gzip.compress(body.toString()), ContentType.APPLICATION_JSON)
    } else {
      new StringEntity(body.toString(), ContentType.APPLICATION_JSON)
    }

    val p = Promise[JsValue]()
    restClient.performRequestAsync(
      "POST",
      endpoint,
      params,
      entity,
      new ResponseListener {
        override def onSuccess(response: Response): Unit = {
          if (response.getEntity.getContentLength > 0) {
            val json: JsValue = getJsonFromResult(response)
            p.success(json)
          } else {
            p.success(JsNull)
          }
          ()
        }

        override def onFailure(exception: Exception): Unit = {
          logger.error(s"error while executing a POST to search", exception)
          logger.error(s"REQUEST: $body")
          p.failure(exception)
          ()
        }
      },
      getHeaders(compressed): _*
    )
    p.future
  }

  def putJson(endpoint: String, body: String, compressed: Boolean, queryParams: (String, String)*): Future[JsValue] = {
    import collection.JavaConverters._
    val params = Map(queryParams: _*).asJava
    val entity: HttpEntity = if (compressed) {
      new ByteArrayEntity(Gzip.compress(body), ContentType.APPLICATION_JSON)
    } else {
      new StringEntity(body, ContentType.APPLICATION_JSON)
    }
    val p = Promise[JsValue]()
    restClient.performRequestAsync(
      "PUT",
      endpoint,
      params,
      entity,
      new ResponseListener {
        override def onSuccess(response: Response): Unit = {
          if (response.getEntity.getContentLength > 0) {
            val json: JsValue = getJsonFromResult(response)
            p.success(json)
          } else {
            p.success(JsNull)
          }
          ()
        }

        override def onFailure(exception: Exception): Unit = {
          logger.error(s"error while executing a PUT (bulk-Operation) to index", exception)
          p.failure(exception)
          ()
        }
      },
      getHeaders(compressed): _*
    )
    p.future
  }

  def delete(endpoint: String, compressed: Boolean, queryParams: (String, String)*): Future[JsValue] = {
    import collection.JavaConverters._
    val params = Map(queryParams: _*).asJava

    val p = Promise[JsValue]()
    restClient.performRequestAsync(
      "DELETE",
      endpoint,
      params,
      new ResponseListener {
        override def onSuccess(response: Response): Unit = {
          val json = Json.parse(response.getEntity.getContent)
          p.success(json)
          ()
        }

        override def onFailure(exception: Exception): Unit = {
          logger.error(s"error while executing a DELETE to index", exception)
          p.failure(exception)
          ()
        }
      },
      getHeaders(compressed): _*
    )
    p.future
  }
}