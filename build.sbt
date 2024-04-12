import scala.collection.Seq
import scala.sys.process.Process


name := """play-blocking-min"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "3.3.3"

routesGenerator := InjectedRoutesGenerator

libraryDependencies += "org.playframework" %% "play-slick" % "6.1.0"
libraryDependencies += "org.playframework" %% "play-slick-evolutions" % "6.1.0"

libraryDependencies += "org.postgresql" % "postgresql" % "42.7.3"
libraryDependencies += ws

ThisBuild / libraryDependencySchemes ++= Seq(
    "org.scala-lang.modules" %% "scala-xml" % VersionScheme.Always
)

//enable displaying of link to intellij in error page
javaOptions += "-Dplay.editor=http://localhost:63342/api/file/?file=%s&line=%s"
