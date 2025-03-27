# Let's do data science on Airport data

The goal of this activity is to create a graph data model from an airport dataset and generate the Cypher to create the graph. Next, we are going to use gdb to run some queries on the graph.

## Create project

```cypher
CALL gds.graph.project(
    'routes',
    'Airport',
    'HAS_ROUTE'
)
YIELD
    graphName, nodeProjection, nodeCount, relationshipProjection, relationshipCount
```

## Run queries

```cypher
CALL gds.graph.list()
```

```cypher
CALL gds.graph.list('routes')
```

```cypher

## Centrality

```cypher
CALL gds.pageRank.stream('routes')
YIELD nodeId, score
WITH gds.util.asNode(nodeId) AS n, score AS pageRank
RETURN n.iata AS iata, n.descr AS description, pageRank
ORDER BY pageRank DESC, iata ASC
```

Save results to a graph

```cypher
// Write pageRank property 
CALL gds.pageRank.write('routes',
    {
        writeProperty: 'pageRank'
    }
)
YIELD nodePropertiesWritten, ranIterations
```

Check our work by running the following query:

```cypher
// Show me the airports with pageRank
MATCH (a:Airport)
RETURN a.iata AS iata, a.descr AS description, a.pageRank AS pageRank
ORDER BY a.pageRank DESC, a.iata ASC
```

## Create a weighted route project

```cypher
CALL gds.graph.project(
    'routes-weighted',
    'Airport',
    'HAS_ROUTE',
    {
        relationshipProperties: 'distance'
    }
)
YIELD
    graphName, nodeProjection, nodeCount, relationshipProjection, relationshipCount
```

## Calculate shortest path

```cypher
MATCH (source:Airport {city: 'Denver'}), (target:Airport {city: 'Melbourne'})
CALL gds.shortestPath.dijkstra.write.estimate('routes-weighted', {
    sourceNode: source,
    targetNodes: target,
    relationshipWeightProperty: 'distance',
    writeRelationshipType: 'PATH'
})
YIELD nodeCount, relationshipCount, bytesMin, bytesMax, requiredMemory
RETURN nodeCount, relationshipCount, bytesMin, bytesMax, requiredMemory
```

## Crime investigation database

Give me the details of all the Crimes under investigation by Officer Larive (Badge Number 26-5234182):

```cypher
MATCH (c:Crime {last_outcome: "Under investigation", type: 'Drugs'})-[i:INVESTIGATED_BY]->(o:Officer {badge_no: "26-5234182", surname: "Larive"})
RETURN c, i, o;
```

Get the shortest path between Jack Powell and Raymond Walker:

```cypher
MATCH (c:Crime {last_outcome: "Under investigation", type: 'Drugs'})-[i:INVESTIGATED_BY]->(o:Officer {badge_no: "26-5234182", surname: "Larive"})
MATCH (start:Person {name: "Jack", surname: "Powell"}), (end:Person {name: "Raymond", surname: "Walker"}) 
MATCH path = shortestPath((start)-[*]-(end)) 
RETURN path;
```
