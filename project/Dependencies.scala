import sbt._

object Dependencies {

  object V {
    val catsEffectVersion = "3.3.11"
    val circeVersion = "0.14.1"
    val circeGenericsExtras = "0.14.1"
    val postgres = "42.3.4"
    val skunk = "0.2.3"
  }

  val circeCore = "io.circe" %% "circe-core" % V.circeVersion
  val circeParser = "io.circe" %% "circe-parser" % V.circeVersion
  val circeGeneric = "io.circe" %% "circe-generic" % V.circeVersion
  val circeOptics = "io.circe" %% "circe-optics" % V.circeVersion
  val catsEffect = "org.typelevel" %% "cats-effect" % V.catsEffectVersion
  val circeGenericsExtras =
    "io.circe" %% "circe-generic-extras" % V.circeGenericsExtras
  val postgres = "org.postgresql" % "postgresql" % V.postgres
  val skunk = "org.tpolecat" %% "skunk-core" % V.skunk
  val skunkCirce = "org.tpolecat" %% "skunk-circe" % V.skunk
}
