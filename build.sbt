organization := "org.yourorganization"

name := "Vacuum cleaner robot sim"

version := "0.1"

scalaVersion := "2.11.4"

scalacOptions ++= Seq("-deprecation", "-unchecked")

artifactName := { (sv: ScalaVersion, module: ModuleID, artifact: Artifact) =>
  artifact.name + "-" + module.revision + "." + artifact.extension
}

initialize ~= { _ => sys.props("scalac.patmat.analysisBudget") = "off" }

scalacOptions ++= Seq(
  "-deprecation",
  "-unchecked",
  "-target:jvm-1.7",
  "-feature",
  "-language:postfixOps"
  // "-optimise" , "-verbose", "-language:reflectiveCalls", "-language:higherKinds" "-language:implicitConversions", "-language:existentials"
)

javacOptions ++= Seq("-g:source,lines,vars", "-source", "1.7", "-target", "1.7")

libraryDependencies ++= {
  Seq(
    "org.scala-lang.modules" %% "scala-xml" % "1.0.2",
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.0.2",
    "ch.qos.logback" % "logback-classic" % "1.1.2",
    "org.scalaz" % "scalaz-core_2.10" % "7.1.0"
  )
}

addCommandAlias("cc", "compile")
