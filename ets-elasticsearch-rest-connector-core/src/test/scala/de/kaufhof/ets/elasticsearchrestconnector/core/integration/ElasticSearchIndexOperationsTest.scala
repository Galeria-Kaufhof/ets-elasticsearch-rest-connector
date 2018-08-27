package de.kaufhof.ets.elasticsearchrestconnector.core.integration

import de.kaufhof.ets.elasticsearchrestconnector.core.client.model.mapping.{IndexSettings, MappingObject}
import org.scalatest._
import utils.{ElasticSearchTestClient, FutureFixture}

class ElasticSearchIndexOperationsTest extends WordSpec with Matchers with ElasticSearchTestClient with FutureFixture {

  "ElasticIndexOperations" should {
    "at the beginning, there is no index, so the spec will be false" in {
      indexExists(indexName) shouldEqual false
    }

    "now create the index" in {
      createIndex(indexName) shouldEqual true
    }

    "check the indexExists again, now with positive result" in {
      indexExists(indexName) shouldEqual true
    }

    "refresh the index" in {
      refreshIndex(indexName) shouldEqual true
    }

    "flush the index" in {
      flushIndex(indexName) shouldEqual true
    }

    "check if another indexName does not exist" in {
      indexExists("newindexname") shouldEqual false
    }

    "remove the index" in {
      deleteIndex(indexName) shouldEqual true
    }

    "check that the index is deleted" in {
      indexExists(indexName) shouldEqual false
    }

  }


  private def indexExists(indexName: String): Boolean = {
    await(standardElasticSearchClient.indexExists(indexName).map(_.exists))
  }

  private def createIndex(indexName: String): Boolean = {
    await(standardElasticSearchClient.createIndex(MappingObject(indexName, None, IndexSettings())).map(_.created))
  }

  private def deleteIndex(indexName: String): Boolean = {
    await(standardElasticSearchClient.deleteIndex(indexName)).deleted
  }

  private def flushIndex(indexName: String): Boolean = {
    await(standardElasticSearchClient.flushIndex(indexName)).flushed
  }

  private def refreshIndex(indexName: String): Boolean = {
    await(standardElasticSearchClient.refreshIndex(indexName)).refreshed
  }


}
