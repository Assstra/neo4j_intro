CREATE 
    (sport:Sports { name: "climbing", location: "Montpellier" }),
    (adrien:Person { name: "Adrien", location: "Montpellier" }),
    (school:School { name: "Polytech", location: "Montpellier" }),
    (company:Company { name: "SLB", location: "Montpellier" }),
    (character:Character { name: "2B", weapon: "sword" }),
    (game:Game { name: "Nier automata", release_date: 2017 }),
    (manga:Manga { name: "nier_automata" }),

    (adrien)-[:PRACTICES { since: 2024 }]->(sport),
    (adrien)-[:LEARNS_IN { since: 2022 }]->(school),
    (adrien)-[:WORKS_IN { role: "cybersecurity apprentice" }]->(company),

    (adrien)-[:LIKES]->(game),
    (character)-[:PLAYS_IN]->(game),
    (game)-[:ADAPTED_IN]->(manga);
