// Create an actor (me) and a movie (Batman) with a 'ACTED_IN' relationship
CREATE
    (p:Person { name: 'Adrien Raimbault', born: '2002' })
    -[:ACTED_IN { role: 'The Joker' }]
    ->(m:Movie { title: 'Batman' })

// Check if the movie exists
MATCH (n:Movie {title:'Batman'})
RETURN n

// Update the relationship property 'role'
MATCH (p:Person {name: 'Adrien Raimbault', born: '2002'})-[r:ACTED_IN {role: 'The Joker'}]-(m:Movie {title: 'Batman'})
SET r.role = 'Catwoman'