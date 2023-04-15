import Dependencies.*

val scala3Version = "3.2.2"

lazy val playground = project
  .in(file("playground"))
  .settings(
    name := "scala-playground",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )

lazy val skunkDemo = project
  .in(file("skunkDemo"))
  .settings(
    name := "scala-playground",
    version := "0.1.0-SNAPSHOT",
    scalaVersion := scala3Version,
    libraryDependencies ++= Seq()
  )
