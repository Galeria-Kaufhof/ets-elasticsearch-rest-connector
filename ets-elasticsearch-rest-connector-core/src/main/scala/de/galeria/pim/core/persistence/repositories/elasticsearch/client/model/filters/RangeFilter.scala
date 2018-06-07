package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.filters

case class RangeFilter(gt: Option[Double] = None, gte: Option[Double] = None, lt: Option[Double] = None, lte: Option[Double] = None, fieldName: String) extends FilterExpression
