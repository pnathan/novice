package novice
import grizzled.slf4j.Logging
import spark.Spark._
import org.neo4j.driver.v1._
import org.neo4j.driver.v1.Values.parameters

object neoManager {
  def dummy () = {

    val driver = GraphDatabase.driver( "bolt://localhost:7687",
      AuthTokens.basic( "neo4j", "password" ) )
    val session = driver.session()

    session.run( "CREATE (a:Person {name: {name}, title: {title}})",
      parameters( "name", "Arthur", "title", "King" ) );

    val result = session.run( "MATCH (a:Person) WHERE a.name = {name} " +
      "RETURN a.name AS name, a.title AS title",
      parameters( "name", "Arthur" ) )
    while ( result.hasNext() )
    {
      val record = result.next();
      System.out.println( record.get( "title" ).asString() + " " + record.get( "name" ).asString() );
    }


  session.close();
    driver.close();
    }
}

object novice extends Logging {
  def main(args: Array[String]) {
    warn("the novice is booting up")
    neoManager.dummy()
    get("/", (req, res) => "indexed")
    get("/hello", (req, res) => "Hello World")
  }

}
