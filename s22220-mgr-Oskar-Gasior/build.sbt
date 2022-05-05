ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.10"

lazy val root = (project in file("."))
  .settings(
    name := "s22220-mgr-Oskar-Gasior"
  )
libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.11" % Test //Unit tests
)
coverageEnabled := true
coverageMinimum := 0
coverageFailOnMinimum := true

