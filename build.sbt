organization := "org.yourorganization"

name := "scala sbt project"

version := "0.1-SNAPSHOT"

scalaVersion := "2.11.1"

scalacOptions ++= Seq("-deprecation", "-unchecked")

libraryDependencies ++= {
  Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
    "ch.qos.logback" % "logback-classic" % "1.1.2",
    "org.scalaz" % "scalaz-core_2.10" % "7.1.0"
  )
}
