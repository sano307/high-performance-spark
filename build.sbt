val Organization = "inseo.io"
val Name = "high-performance-spark"
val Version = "0.0.1"
val ScalaVersion = "2.12.8"

lazy val commonSettings = Seq(
  organization := Organization,
  name := Name,
  version := Version,
  scalaVersion := ScalaVersion,
  scalacOptions := Seq(
    "-deprecation",
    "-feature",
    "-unchecked",
    "-Ypartial-unification",
    "-target:jvm-1.8"
  ),
  resolvers += "Sonatype Releases" at "https://oss.sonatype.org/content/repositories/releases/",
  libraryDependencies ++= Seq(
    library.sparkCore,
    library.sparkSql
  )
)

lazy val root = (project in file("."))
  .settings(commonSettings)

lazy val library = 
  new {
    object Version {
      val spark = "2.4.1"
    }
    val sparkCore = "org.apache.spark" %% "spark-core" % Version.spark
    val sparkSql = "org.apache.spark" %% "spark-sql" % Version.spark
  }
