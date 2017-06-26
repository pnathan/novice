package novice
import java.time.Instant
import java.util.Date

import grizzled.slf4j.Logging
import main.scala.novice.{NeoConnect, NoviceConfig}
import java.text.{SimpleDateFormat, DateFormat}
import spark.Spark._
import org.neo4j.driver.v1._
import org.neo4j.driver.v1.Values.parameters
import Analysis.Affiliation

object Analysis {
  type Affiliation = String
}

abstract class EntityKind {
  def name: String
}
case class Person() extends  EntityKind {
  def name = "Person"
}
case class Group() extends EntityKind {
  def name = "Group"
}

/***
  * An Entity is is a character of interest. Might be a group, a person, etc.
  * @param id archive id TODO
  * @param name name of the entity. If a person, it will be the Standard Usage of it, ref. Wikipedia.
  *             That is, e.g., "Barack Obama", or "Kim Jong-un".
  * @param affiliation Political affiliation...
  * @param sampleDate ...at a certain time.
  * @param notes Further remarks.
  */
case class Entity(classOfEntity: EntityKind, name: String, affiliation: Affiliation, sampleDate: String, notes: String) {
  def insert(session: Session): Unit = {
    val result = session.run(
      s"""CREATE (a:${classOfEntity.name}
         |{name: {name},
         | affiliation: {affiliation},
         | sampleDate: {sampleDate},
         | notes: {notes}
         | })
       """.stripMargin,
      parameters(
        "name", name,
        "affiliation", affiliation,
        "sampleDate", sampleDate,
        "notes", notes))
  }
}
object Entity {
  def getByName(session:Session, entityKind: EntityKind, name: String): Seq[Entity] = {
    val result = session.run(
      s"""MATCH (a:${entityKind.name}) WHERE a.name = {name}
         |
                | RETURN a.name AS name, a.title AS title, a.affiliation as affiliation, a.sampleDate as sampleDate, a.notes as notes
              """.stripMargin,
      parameters("name", name))
    val accumulator = scala.collection.mutable.ListBuffer.empty[Entity]

    while (result.hasNext()) {
      val record = result.next()

      val found = Entity(Person(),
        record.get("name").asString(),
        record.get("affiliation").asString(),
        record.get("sampleDate").asString(),
        record.get("notes").asString())

      accumulator.append(found)
    }
    accumulator.toList
  }
}

/***
  * A MemeticCarrier is an entity such as a book, a website, or other carrier of an idea.
  *
  * @param id archive id TODO
  * @param name name
  * @param affiliation political affiliation
  * @param notes further remarks.
  */
case class MemeticCarrier(name: String, affiliation: Affiliation, notes: String){

}

object novice extends Logging {
  def main(args: Array[String]) {
    warn("the novice is booting up")
    val conf = NoviceConfig.getConfig()
    val connection = NeoConnect(conf.neo4jhost, conf.neo4jport, conf.neo4juser, conf.neo4jpass)

    get("/", (req, res) => "welcome to the novice. ")

    path("/api/v1/person", () => {
      get("/:name", (req, res) => {
        val name = req.params(":name")
        val results = connection.withSession((session) =>
          Entity.getByName(session, Person(), name))
        results
      })
      //get("/:id", (req, res) => "")
      // notes go in the body
      put("/:name/:sampleDate/:affiliation/", (req, res) => {
        val name = req.params(":name")
        val sampleDateStr = req.params(":sampleDate")
        val affiliation = req.params(":affiliation")
        val notes = req.body()

        val df1 = new SimpleDateFormat("yyyy-MM-dd")

        // Verify we can parse the string.... then move on.
        val sampleDate = df1.parse(sampleDateStr)


        val newPerson = Entity(Person(), name, affiliation, sampleDateStr, notes)
        connection.withSession((session) =>
          newPerson.insert(session))
        "inserted"
      })
    })
  }
}
