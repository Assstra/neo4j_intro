WITH 'https://raw.githubusercontent.com/Assstra/neo4j_intro/refs/heads/main/activity_4/test_data.json' AS url

CALL apoc.load.json(url) YIELD value as person
MERGE (p:Person {name: person.name})
   ON CREATE SET p.age = person.age, p.children = size(person.children)