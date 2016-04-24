name := "blazinandthegoons"

version := "1.0"

lazy val `blazinandthegoons` = (project in file(".")).enablePlugins(PlayJava)

scalaVersion := "2.11.1"

libraryDependencies ++= Seq( javaJdbc , javaEbean , cache , javaWs )

libraryDependencies ++= Seq(
  cache,
  //angular2 dependencies
  "org.webjars.npm" % "angular2" % "2.0.0-beta.14",
  "org.webjars.npm" % "systemjs" % "0.19.26",
  "org.webjars.npm" % "todomvc-common" % "1.0.2",
  "org.webjars.npm" % "rxjs" % "5.0.0-beta.2",
  "org.webjars.npm" % "es6-promise" % "3.0.2",
  "org.webjars.npm" % "es6-shim" % "0.35.0",
  "org.webjars.npm" % "reflect-metadata" % "0.1.2",
  "org.webjars.npm" % "zone.js" % "0.6.6",
  "org.webjars.npm" % "typescript" % "1.8.10",

  //tslint dependency
  "org.webjars.npm" % "tslint-eslint-rules" % "1.2.0"
)

dependencyOverrides += "org.webjars.npm" % "minimatch" % "2.0.10"


unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  