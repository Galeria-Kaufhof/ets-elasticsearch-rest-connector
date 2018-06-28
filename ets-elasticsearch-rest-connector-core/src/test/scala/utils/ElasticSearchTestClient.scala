package utils

import java.io.File
import java.util.concurrent.TimeUnit

import de.kaufhof.ets.elasticsearchrestconnector.core.connector.{ElasticSearchConfiguration, ElasticSearchUriParser, RestClientBuilderImpl, StandardElasticSearchClient}
import org.apache.http.client.config.RequestConfig
import org.elasticsearch.client.{RestClient, RestClientBuilder}
import org.scalatest.{BeforeAndAfterAll, WordSpecLike}
import pl.allegro.tech.embeddedelasticsearch.{EmbeddedElastic, PopularProperties}

import scala.concurrent.ExecutionContext.Implicits.global

trait ElasticSearchTestClient extends WordSpecLike with BeforeAndAfterAll {
  override def afterAll(): Unit = {
    super.afterAll()
    standardElasticSearchClient.closeClient()
  }

  protected val elasticPort: Int = 9210
  protected val elasticAddress: String = "localhost"
  protected val clusterName: String = "clusterA"
  protected val indexName: String = "index1"

  protected val compressCommunicationB: Boolean = true

  private final val elasticUri: String = s"elasticsearch://$elasticAddress:$elasticPort/$clusterName/$indexName"
  private final val maybeSearchConfiguration: Option[ElasticSearchConfiguration] = ElasticSearchUriParser(elasticUri)

  if(maybeSearchConfiguration.isEmpty){
    throw new RuntimeException("error while building the ElasticSearchConfiguration")
  }

  val elasticSearchConfiguration: ElasticSearchConfiguration = maybeSearchConfiguration.get

  private val elasticRestClient: RestClient = RestClientBuilderImpl.build(elasticSearchConfiguration.hostList).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback {
    override def customizeRequestConfig(requestConfigBuilder: RequestConfig.Builder): RequestConfig.Builder = {
      requestConfigBuilder
        .setConnectTimeout(15000)
        .setSocketTimeout(60000)
        .setConnectionRequestTimeout(15000)
    }
  }).setMaxRetryTimeoutMillis(60000)
    .build()

  val standardElasticSearchClient: StandardElasticSearchClient = new StandardElasticSearchClient(elasticRestClient){
    override val compressCommunication: Boolean = compressCommunicationB
  }
}
