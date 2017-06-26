package main.scala.novice

import com.typesafe.config.ConfigFactory
import grizzled.slf4j.Logging

/**
  * Typed configuration object.
  * @param env
  */
case class NoviceConfig(env: String, username: String, password: String) extends Logging{
  def neo4juser: String = username
  def neo4jpass: String = password

  // probably will need tweaking. Another day.
  def neo4jport: Int = 7687



  def neo4jhost: String = {
    env match {
      case "compose" => "neo4j"
      case "dev" => "localhost"
      case _ => {
        error(s"Environment configured as $env, not understood")
        throw new IllegalStateException("Unable to process environment configuration: exiting")
      }
    }
  }
}

object NoviceConfig {
  lazy private val config = ConfigFactory.load()
  def getConfig() : NoviceConfig = {
    NoviceConfig(config.getString("env"), config.getString("username"), config.getString("password"))
  }
}