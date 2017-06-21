import Dependencies._

lazy val root = (project in file(".")).
  settings(
    inThisBuild(List(
      organization := "org.ababup1192",
      scalaVersion := "2.12.2",
      version      := "0.1.0-SNAPSHOT"
    )),
    name := "Play Json Sample",
    libraryDependencies ++= Seq(
      "com.typesafe.play" %% "play-json" % "2.6.0",
      "com.typesafe.play" %% "play-ahc-ws-standalone" % "1.0.0-M10",
      scalaTest % Test
    )
  )
