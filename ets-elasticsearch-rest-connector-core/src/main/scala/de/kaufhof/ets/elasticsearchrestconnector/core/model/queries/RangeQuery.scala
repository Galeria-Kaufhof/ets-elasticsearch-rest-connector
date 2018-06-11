package de.kaufhof.ets.elasticsearchrestconnector.core.model.queries

case class RangeQuery(
                       gt: Option[Double] = None,
                       gte: Option[Double] = None,
                       lt: Option[Double] = None,
                       lte: Option[Double] = None,
                       fieldName: String, boost: Double = 1.0
                     ) extends QueryExpression
