WITH 'https://api.stackexchange.com/2.2/questions?pagesize=100&order=desc&sort=creation&tagged=neo4j&site=stackoverflow&filter=!5-i6Zw8Y)4W7vpy91PMYsKM-k9yzEsSC1_Uxlf' AS url

CALL apoc.load.json(url) YIELD value
UNWIND value.items AS item
MERGE (q:Question { id:item.question_id }) ON CREATE
  SET q.title = item.title, q.body = item.body_markdown
MERGE (user:User { id: item.owner.user_id, name: item.owner.display_name})
MERGE (user)-[:ASKED]->(q)

WITH q, item.tags AS tags, item.answers AS answers
UNWIND tags AS tag
MERGE (t:Tag {tag: tag})
MERGE (q)-[:TAGGED]->(t)

WITH q, answers
UNWIND answers AS answer
MERGE (user:User { id: answer.owner.user_id, name: answer.owner.display_name})
MERGE (a:Answer {id: answer.answer_id, title: answer.title, body: answer.body_markdown})
MERGE (user)-[:PROVIDED]->(a)
MERGE (a)-[:ANSWERS]->(q)

