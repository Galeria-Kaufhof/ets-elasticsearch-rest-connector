package de.kaufhof.ets.elasticsearchrestconnector.core.integration

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.{IndexSettings, Mapping, MappingObject, MappingType}
import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.template.IndexTemplate
import org.scalatest.{Matchers, WordSpec}
import utils.{ElasticSearchTestClient, FutureFixture}

class ElasticTemplateOperationsTest extends WordSpec with Matchers with ElasticSearchTestClient with FutureFixture {

  final val templateName: String = "template1"
  final val newIndexName: String = "my_nice_supadupa_index"


  "ElasticTemplateOperations" should {

    "at the beginning, there is no template in es" in {
      templateExists(templateName) shouldEqual false
    }

    "then we fill a template" in {
      val t: IndexTemplate = IndexTemplate(List("my_nice_*"), IndexSettings(totalFieldLimit = 666), Mapping("myMapping", MappingType.Strict, dateDetection = false, numericDetection = true, dynamicTemplates = List(),properties = List()))

      addTemplate(templateName, t) shouldEqual true
    }

    "check if the template exists" in {
      templateExists(templateName) shouldEqual true
    }

    "create an index that match the pattern" in {
      createEmptyIndex(newIndexName) shouldEqual true
    }

    "check if the created index has an appropriated templatevalue" in {
      getIndexValueFieldLimit(newIndexName) shouldEqual 666
    }

    "create a default index with a different pattern" in {
      createIndexWithDefaults(indexName)
    }

    "check if another index with a different pattern, does not have the special value" in {
      getIndexValueFieldLimit(indexName) shouldEqual 1000
    }

    "remove the index" in {
      removeIndex(newIndexName) shouldEqual true
    }

    "remove the default index" in {
      removeIndex(indexName) shouldEqual true
    }

    "last we remove the template" in {
      removeTemplate(templateName) shouldEqual true
    }

    "and check if the template is removed" in {
      templateExists(templateName) shouldEqual false
    }

  }


  def templateExists(templateName: String): Boolean = {
    await(standardElasticSearchClient.templateExits(templateName)).exists
  }

  def addTemplate(templateName: String, indexTemplate: IndexTemplate): Boolean = {
    await(standardElasticSearchClient.addTemplate(templateName, indexTemplate)).acknowledged
  }

  def createEmptyIndex(indexName: String): Boolean = {
    await(standardElasticSearchClient.createEmptyIndex(indexName).map(_.created))
  }

  def createIndexWithDefaults(indexName: String): Boolean = {
    await(standardElasticSearchClient.createIndex(MappingObject(indexName, Some(Mapping("mapping1", MappingType.Strict, dateDetection = false, numericDetection = false, dynamicTemplates = List(), properties = List())), IndexSettings()))).created
  }

  def getIndexValueFieldLimit(indexName:String): Int = {
    await(standardElasticSearchClient.getIndex(indexName)).index.flatMap{jso =>
      (jso \ indexName \ "settings" \ "index" \ "mapping" \ "total_fields" \ "limit").asOpt[String].map(_.toInt)
    }.getOrElse(0)
  }

  def removeTemplate(templateName: String): Boolean = {
    await(standardElasticSearchClient.removeTemplate(templateName)).deleted
  }

  def removeIndex(indexName: String): Boolean = {
    await(standardElasticSearchClient.deleteIndex(indexName)).deleted
  }

}
