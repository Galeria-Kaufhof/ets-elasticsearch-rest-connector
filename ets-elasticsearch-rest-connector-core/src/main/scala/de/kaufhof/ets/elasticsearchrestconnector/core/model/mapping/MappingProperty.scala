package de.galeria.pim.core.persistence.repositories.elasticsearch.client.model.mapping

import play.api.libs.json._

import scala.util.Try

trait MappingProperty

object MappingProperty {

  def apply(mappingProperty: MappingProperty): (String, JsValue) = {
    mappingProperty match {
      case PercolatorMappingProperty      => TypeMappingProperty(TypeMappingProperty(EnumMappingTypes.Percolator))
      case s: StringMappingProperty       => StringMappingProperty(s)
      case l: LongMappingProperty         => LongMappingProperty(l)
      case b: BooleanMappingProperty      => BooleanMappingProperty(b)
      case o: ObjectMappingProperty       => ObjectMappingProperty(o)
      case t: TypeMappingProperty         => TypeMappingProperty(t)
      case k: KeywordMappingProperty      => KeywordMappingProperty(k)
      case q: QueryMappingProperty        => QueryMappingProperty(q)
      case j: JoinMappingProperty         => JoinMappingProperty(j)
    }
  }
}

