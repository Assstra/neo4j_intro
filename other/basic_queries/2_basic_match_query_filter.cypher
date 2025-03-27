MATCH (m:Movie)
WHERE m.released < 2005
RETURN m.title, m.released

// We can also make it shorter if we are looking for a property value
MATCH (p:Person {name: "Tom Hanks"})
RETURN p