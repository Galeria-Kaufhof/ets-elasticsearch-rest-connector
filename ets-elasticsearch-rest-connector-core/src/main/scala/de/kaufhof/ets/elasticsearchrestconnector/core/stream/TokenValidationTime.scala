package de.kaufhof.ets.elasticsearchrestconnector.core.stream

import scala.concurrent.duration.FiniteDuration

case class TokenValidationTime(finiteDuration: FiniteDuration){
  override def toString: String = s"${finiteDuration.toSeconds}s"
}
