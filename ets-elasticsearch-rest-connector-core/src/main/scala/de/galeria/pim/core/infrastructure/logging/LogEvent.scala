package de.galeria.pim.core.infrastructure.logging

import java.util

import org.slf4j.Marker


sealed class Epic extends Marker {
  override def getName: String = ???

  override def add(marker: Marker): Unit = ???

  override def remove(marker: Marker): Boolean = ???

  override def hasChildren: Boolean = ???

  override def hasReferences: Boolean = ???

  override def iterator(): util.Iterator[Marker] = ???

  override def contains(marker: Marker): Boolean = ???

  override def contains(s: String): Boolean = ???
}

object JobMonitoring extends Epic

object MCLS extends Epic

object Core extends Epic

object Enhance extends Epic

object Api extends Epic

object Search extends Epic

object DB extends Epic

object SFTP extends Epic

object ImportJob extends Epic

object VariantAssetsImportJob extends Epic

object MediaAssetsImportJob extends Epic

object MediaAssetsCSyncJob extends Epic

object NameGeneratorEnhancer extends Epic

object VariantAssetsRepair extends Epic

object TradebyteImportJob extends Epic

object TradebyteBucketConversion extends Epic

object Tenant extends Epic

object Migration extends Epic

object Feeds extends Epic

object Auth extends Epic

object ContentSnapshot extends Epic

object ConstraintWarning extends Epic

object Campaigns extends Epic

object RequestTimes extends Epic

object MasterDataSource extends Epic

object Scheduler extends Epic

object WwsImport extends Epic

object PimEvents extends Epic

object Maintenance extends Epic


/**
  * CopyJobMonitoring is used to identify log messages shown in the Kibana dashboard for OSM users of the
  * copy job.
  */
object CopyJobMonitoring extends Epic
