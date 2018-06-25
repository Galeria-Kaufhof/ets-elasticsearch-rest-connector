package utils

import scala.concurrent.duration.{FiniteDuration, _}
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.util.Try

trait FutureFixture {
  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global

  def await[T](f: => Future[T], timeout: FiniteDuration = 5.seconds): T = Await.result(f, timeout)
  def awaitCatch[T](f: => Future[T], timeout: FiniteDuration = 5.seconds): Try[T] = Try(Await.result(f, timeout))
}