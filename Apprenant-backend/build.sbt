ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.7"

lazy val root = (project in file("."))
  .settings(
    name := "Apprenant-backend",

    libraryDependencies ++= Seq(
      // http4s
      "org.http4s" %% "http4s-ember-server" % "0.23.33",
      "org.http4s" %% "http4s-dsl"          % "0.23.33",
      "org.http4s" %% "http4s-circe"        % "0.23.33",

      // Akka
      "com.typesafe.akka" %% "akka-actor-typed" % "2.8.8",
      "com.typesafe.akka" %% "akka-stream"      % "2.8.8",

      // JSON
      "io.circe" %% "circe-generic" % "0.14.15",

      // Logging
      "ch.qos.logback" % "logback-classic" % "1.5.20"
    )
  )