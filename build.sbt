name := "Spark Project"
version := "1.0"
organization := "telefonica/chile"

scalaVersion := "2.11.8"

resolvers += "Spark Packages Repo" at "http://dl.bintray.com/spark-packages/maven"

resolvers += "Typesafe Releases" at "https://dl.bintray.com/typesafe/ivy-releases/"

libraryDependencies += "com.typesafe" % "config" % "1.4.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.3.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.3.0" % "provided",
  "org.apache.spark" %% "spark-hive" % "2.3.0" % "provided"
)
