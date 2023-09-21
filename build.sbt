import sbtcrossproject.{crossProject, CrossType}

lazy val server = (project in file("server")).settings(commonSettings).settings(
    name := "smolkraft_server",
    scalaJSProjects := Seq(client),
    Assets / pipelineStages := Seq(scalaJSPipeline),
    pipelineStages := Seq(digest, gzip),
  // triggers scalaJSPipeline when using compile or continuous compilation
    Compile / compile := ((Compile / compile) dependsOn scalaJSPipeline).value,
    libraryDependencies ++= Seq(
      guice,
      "com.vmunier" %% "scalajs-scripts" % "1.1.3",
      "org.scalatestplus.play" %% "scalatestplus-play" % "5.1.0" % Test,
      "com.typesafe.play" %% "play-slick" % "5.0.0",
      "com.typesafe.slick" %% "slick" % "3.3.2",
      "com.typesafe.slick" %% "slick-codegen" % "3.3.2",
      "org.postgresql" % "postgresql" % "42.2.11",
      "org.mindrot" % "jbcrypt" % "0.4",
      "joda-time" % "joda-time" % "2.10.13",
      specs2 % Test
)
).enablePlugins(PlayScala).dependsOn(sharedJvm)

lazy val client = (project in file("client")).settings(commonSettings).settings(
  name := "smolkraft_client",
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.1" cross CrossVersion.full),
  scalacOptions += "-P:scalajs:sjsDefinedByDefault",
  scalaJSUseMainModuleInitializer := true,
  libraryDependencies ++= Seq(
      "org.scala-js" %%% "scalajs-dom" % "0.9.7",
      "org.querki" %%% "jquery-facade" % "1.2",
      "me.shadaj" %%% "slinky-core" % "0.6.3",
      "me.shadaj" %%% "slinky-web" % "0.6.3",
      "com.typesafe.play" %% "play-json" % "2.8.1"
)
).enablePlugins(ScalaJSPlugin, ScalaJSWeb).
dependsOn(sharedJs)
version := "1.0-SNAPSHOT"

lazy val shared = crossProject(JSPlatform, JVMPlatform)
  .crossType(CrossType.Pure)
  .in(file("shared"))
  .settings(
    name := "smolkraft_shared",
    commonSettings,
    libraryDependencies ++= Seq(
      "com.typesafe.play" %%% "play-json" % "2.8.1"
))
lazy val sharedJvm = shared.jvm
lazy val sharedJs = shared.js

lazy val commonSettings = Seq(
  scalaVersion := "2.12.10",
  organization := "com.RobyData"
)

// loads the server project at sbt startup
onLoad in Global := (onLoad in Global).value andThen {s: State => "project server" :: s}

//lazy val root = (project in file(".")).enablePlugins(PlayScala)



//libraryDependencies += "com.github.nscala-time" %% "nscala-time" % "2.32.0"
//libraryDependencies += "com.typesafe.slick" %% "slick-hikaricp" % "3.3.2"


// Adds additional packages into Twirl
//TwirlKeys.templateImports += "com.RobyData.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "com.RobyData.binders._"
