novice
---

# about

To facilitate learning, a novice must have an library for
study. Further, the novice must take notes and form understandings of
the context of a situation.

This tool is a web application to allow studious *novices* to form
connections in a history of ideas and people.


# status

- no UI.

Currently implemented routes:

- PUT /api/v1/person/:name/:date-sampled-at/:affiliation w/ body for notes.
- GET /api/v1/person/:name -> Scala object toString

# API TO IMPLEMENT

- GET /api/v1/person/:name -> **JSON back**
GET /api/v1/person/:id -> JSON back

PUT /api/v1/group/:name/:affiliation w/ body for notes.
GET /api/v1/group/:name -> JSON back
GET /api/v1/group/:id -> JSON back

PUT /api/v1/meme/:name/:affiliation w/ body for notes.
GET /api/v1/meme/:name -> JSON back
GET /api/v1/meme/:id -> JSON back

PUT /api/v1/connection/:relationship/:srcid/:dstid w/ body for notes
GET /api/v1/connection/relatedto/:id

# TODO IN CODE

* UI
* Connection class for relationship describing: this should be a neo4j edge in the graph.
* Deployment system to run locally in an on-desktop release fashion
and store the neo4j DB locally.
* Add authN system.
* Deployment system to run on a cloud server


# LICENSE

AGPL3 - https://www.gnu.org/licenses/agpl-3.0.en.html
