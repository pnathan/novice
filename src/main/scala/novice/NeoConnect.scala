package main.scala.novice

import org.neo4j.driver.v1.{AuthTokens, GraphDatabase, Session}

case class NeoConnect(host: String, port: Int, username: String, password: String) {
  val driver = GraphDatabase.driver(s"bolt://$host:${port.toString}",
    AuthTokens.basic(username, password))

  def withSession[A]( f : Session => A  ) = {
    val session: Session = driver.session()
    val result = f(session)
    session.close()
    result
  }

  def closeConnection = {
    driver.close()
  }
}
