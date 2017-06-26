/*
build.sbt for novice
 */


lazy val commonSettings = Seq(
  organization := "com.example",
  version := "0.1.0",
  // set the Scala version used for the project
  scalaVersion := "2.12.2"
)


lazy val root = (project in file("."))
  .settings(
    commonSettings,

    // set the name of the project
    name := "novice",

    // set the main Scala source directory to be <base>/src
    scalaSource in Compile := baseDirectory.value / "src",

    // set the Scala test source directory to be <base>/test
    scalaSource in Test := baseDirectory.value / "test",

    // increase the time between polling for file changes when using continuous execution
    pollInterval := 1000,

    // append -deprecation to the options passed to the Scala compiler
    scalacOptions += "-deprecation",

    // disable updating dynamic revisions (including -SNAPSHOT versions)
    offline := true,

    // fork a new JVM for 'run' and 'test:run'
    fork := true,

    // add a JVM option to use when forking a JVM for 'run'
    javaOptions += "-Xmx2G",

    // only use a single thread for building
    parallelExecution := false,

    // Execute tests in the current project serially
    //   Tests from other projects may still run concurrently.
    parallelExecution in Test := false,
    libraryDependencies ++= Seq(
      "org.clapper" %% "grizzled-slf4j" % "1.3.1",
      "com.sparkjava" % "spark-core" % "2.6.0" ,
      "org.neo4j.driver" % "neo4j-java-driver" % "1.2.1",
      "ch.qos.logback" % "logback-classic" % "1.0.13",
      "com.typesafe" % "config" % "1.3.1",
      "org.json4s" %% "json4s-jackson" % "3.5.2"
    )


  )
