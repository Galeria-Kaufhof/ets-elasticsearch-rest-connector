package de.galeria.pim.core.infrastructure.logging

import com.typesafe.scalalogging.Logger
import org.slf4j.LoggerFactory

trait TracedLogging {

  protected lazy val log = Logger(LoggerFactory.getLogger(getClass))

}
