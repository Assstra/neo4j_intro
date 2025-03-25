MATCH (m:Movie)-[a:ACTED_IN]-(p:Person)
WITH m, COUNT(a) AS ActorCount
WHERE ActorCount > 1
RETURN m, ActorCount