name := """smolkraft"""
organization := "com.RobyData"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.13.11"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test
libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.32.0"

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.RobyData.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.RobyData.binders._"
