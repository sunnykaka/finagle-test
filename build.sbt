name := "finagle-test"

version := "1.0"

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  "com.twitter" %% "finagle-core" % "6.31.0",
  "com.twitter" %% "finagle-http" % "6.31.0",
  "com.twitter" %% "finagle-serversets" % "6.31.0"
)
