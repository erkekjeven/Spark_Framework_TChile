name := "Spark Project"
version := "1.0"
organization := "telefonica/chile"

scalaVersion := "2.11.8"

resolvers += "Spark Packages Repo" at "https://repos.spark-packages.org/"

resolvers += "Typesafe Releases" at "https://repo.typesafe.com/typesafe/ivy-releases/"

resolvers += "Clojars" at "http://clojars.org/repo/"

libraryDependencies += "com.typesafe" % "config" % "1.4.0"

libraryDependencies += "com.microsoft.sqlserver" % "mssql-jdbc" % "7.4.1.jre12"

libraryDependencies += "com.databricks" %% "spark-xml" % "0.10.0"

libraryDependencies ++= Seq(
  "org.apache.spark" %% "spark-core" % "2.3.0" % "provided",
  "org.apache.spark" %% "spark-sql" % "2.3.0" % "provided",
  "org.apache.spark" %% "spark-hive" % "2.3.0" % "provided"
)
