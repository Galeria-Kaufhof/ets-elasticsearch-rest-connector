package de.galeria.pim.core.infrastructure.logging

case class TraceLocalLogInformation(jobId: Option[String], vid: Option[String],bid: Option[String], uid: Option[String], tid: Option[String]) {

  def toMap: Map[String, String] = {
    Map(
      "jobid" -> jobId,
      "vid" -> vid,
      "bid" -> bid,
      "uid" -> uid,
      "tid" -> tid
    ).filter(_._2.isDefined).mapValues(_.get)
  }

}
