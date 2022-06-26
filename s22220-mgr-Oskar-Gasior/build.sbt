ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.12.10"
val sparkVersion = "3.1.2"

lazy val root = (project in file("."))
  .settings(
    name := "s22220-mgr-Oskar-Gasior"
  )

val sparkDependencies = Seq(
  "org.apache.spark" %% "spark-core" % sparkVersion,
  "org.apache.spark" %% "spark-sql" % sparkVersion
)

val testDependencies = Seq(
  "org.scalatest" %% "scalatest" % "3.2.11" % Test //Unit tests
)

libraryDependencies ++= testDependencies ++ sparkDependencies

coverageEnabled := true
coverageMinimum := 0
coverageFailOnMinimum := true

