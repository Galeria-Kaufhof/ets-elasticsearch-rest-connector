# Indexing

## Create an index
Based on this documentaion. For now, it is only implemented that you can
create an index with a Mappingobject or a Json-Object.

    elasticClient.createIndex(INDEXNAME, MAPPINGOBJECT): Future[ElasticIndexCreateResult]
    
    ElasticIndexCreateResult.created[Boolean]
    ElasticIndexCreateResult.hasError[Boolean]
    ElasticIndexCreateResult.errorMessage[Boolean]