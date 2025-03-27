// Get CVE json file values

WITH “file:///nvdcve-1.1–2018.json” as url 
CALL apoc.load.json(url) YIELD value 
UNWIND keys(value) AS key
RETURN key, apoc.meta.cypher.type(value[key]);