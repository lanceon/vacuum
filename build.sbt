organization := "com.webitoria"

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
    "org.scalaz" % "scalaz-core_2.10" % "7.1.0",
    "org.scalaz" % "scalaz-concurrent_2.10" % "7.1.0"
  )
}

seq(webSettings: _*)

// lift, jetty
libraryDependencies ++= {
  val liftVersion = "2.6-RC2"
  Seq(
    "net.liftweb" %% "lift-webkit" % liftVersion % "compile",
    "org.eclipse.jetty.orbit" % "javax.servlet" % "3.0.0.v201112011016" % "container,compile" artifacts Artifact("javax.servlet", "jar", "jar"),
    "org.eclipse.jetty" % "jetty-webapp" % "9.2.2.v20140723" % "container",
    "org.eclipse.jetty" % "jetty-plus"   % "9.2.2.v20140723" % "container"
  )
}

// loggers
libraryDependencies ++= {
  Seq(
    "ch.qos.logback"   % "logback-classic"    % "1.1.2",
    "org.slf4j"        % "slf4j-api"          % "1.7.7"
  )
}

port in container.Configuration := 8080

addCommandAlias("cc", "compile")

addCommandAlias("cs", "container:start")
