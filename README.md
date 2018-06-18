# ets-elasticsearch-rest-connector

## description
The connector uses the RESTful api of an elastic-server to .
Currently it supports the elastic-version 6.2.2

## initialization
first of all, you need an uri to the elasticsearch server or cluster contact node.
for example, if you have a local running elasticnode a valid uri is
    
    val elasticUri = elasticsearch://localhost:9200/[CLUSTERNAME]/[INDEXNAME]
 
where Clustername and Indexname are the identifier where the structured data in elasticsearch is stored.
With the uri you build an ElasticSearchConfiguration

    val maybeSearchConfiguration: Option[ElasticSearchConfiguration] = ElasticSearchUriParser(elasticUri)
    
If the configuration is none, there is a problem with the uri-preparation, you must look how you deal with
this problem (e.g. Throw a runtimeexception)
After you check if the configuration is not none, you can get the real object in the simplest way.

    val elasticSearchConfiguration: ElasticSearchConfiguration = maybeSearchConfiguration.get

The next step ist to build a restclient. Here is an example of such an object with some timeout settings. 

      val elasticRestClient: RestClient = RestClient.builder(elasticSearchConfiguration.hostList: _*).setRequestConfigCallback(new RestClientBuilder.RequestConfigCallback {
        override def customizeRequestConfig(requestConfigBuilder: RequestConfig.Builder): RequestConfig.Builder = {
          requestConfigBuilder
            .setConnectTimeout(15000)
            .setSocketTimeout(60000)
            .setConnectionRequestTimeout(15000)
        }
      }).setMaxRetryTimeoutMillis(60000)
        .build()

More informations about the RestClient you find here

https://www.elastic.co/guide/en/elasticsearch/client/java-api/6.2/client.html

Now, you can define a new ElasticSearchClient with the prior builded restclient.

    val elasticClient: StandardElasticSearchClient = new StandardElasticSearchClient(elasticRestClient)
    
From here you can use the elasticClient for all your operations (index, search, percolate ...)

All examples uses the elasticClient-Object from this page as base to work with.
Let's take a look to the different parts of this driver:


## Full documentation

See the [ElasticSearch REST Connector chapter](https://galeria-kaufhof.github.io/ets-documentation/ElasticSearch-REST-Connector/) on the ETS documentation portal.
