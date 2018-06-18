# Indexing

## Create an index
Based on this documentaion. For now, it is only implemented that you can
create an index with a Mappingobject or a Json-Object.

    elasticClient.createIndex(INDEXNAME, MAPPINGOBJECT): Future[ElasticIndexCreateResult]

Each createIndex resulted in a scala.concurrent.Future[ElasticIndexCreateResult] with the following ReturnParameter:
    
    ElasticIndexCreateResult.created[Boolean]
    ElasticIndexCreateResult.throwable[Option[Throwable]]

If the createIndex resulted without errors, .throwable is None, if not, throwable ist filled as Some(throwable) with the underlying exception.
