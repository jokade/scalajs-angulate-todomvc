name := "scalajs-angulate-todomvc"

val commonSettings = Seq(
  organization := "biz.enef",
  version := "0.1-SNAPSHOT",
  scalaVersion := "2.11.2",
  scalacOptions ++= Seq("-deprecation","-feature","-Xlint"),
  resolvers += "karchedon-repo" at "http://maven.karchedon.de/"
)

val angulateDebugFlags = Seq(
  "runtimeLogging",
  "ModuleMacros.debug"
  //"ControllerMacros.debug"
  //"DirectiveMacros.debug"
  //"ServiceMacros.debug"
//  "HttpPromiseMacros.debug"
).map( f => s"-Xmacro-settings:biz.enef.angular.$f" )


lazy val jvm = project.
  settings(commonSettings: _*).
  settings(
    libraryDependencies ++= Seq(
      "de.karchedon" %% "surf-akka-rest" % "0.1-SNAPSHOT"
    )
  )
  
lazy val js = project.
  settings(commonSettings: _*).
  settings(
    scalacOptions ++= angulateDebugFlags,
    libraryDependencies ++= Seq(
      "biz.enef" %%% "scalajs-angulate" % "0.1-SNAPSHOT"
    )
  ).
  enablePlugins(ScalaJSPlugin)
  

// standalone application (server+web client in a single fat JAR)
lazy val app = project.in( file(".") ).
  dependsOn( jvm ).
  settings(commonSettings:_*).
  settings(
    // build JS and add JS resources
    (compile in Compile) <<= (compile in Compile).dependsOn(fastOptJS in (js,Compile)),
    mainClass in (Compile,run) := Some("todomvc.example.Server")
  )
    
