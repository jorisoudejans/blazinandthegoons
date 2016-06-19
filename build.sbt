name := "blazinandthegoons"

version := "1.0"

lazy val `blazinandthegoons` = (project in file(".")).enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

incOptions := incOptions.value.withNameHashing(true)

updateOptions := updateOptions.value.withCachedResolution(cachedResoluton = true)

libraryDependencies ++= Seq( javaJdbc , cache , javaWs )

libraryDependencies ++= Seq(
  "org.webjars.npm" % "angular2" % "2.0.0-beta.14",
  "org.webjars.npm" % "systemjs" % "0.19.26",
  "org.webjars.npm" % "todomvc-common" % "1.0.2",
  "org.webjars.npm" % "rxjs" % "5.0.0-beta.2",
  "org.webjars.npm" % "es6-promise" % "3.0.2",
  "org.webjars.npm" % "es6-shim" % "0.35.0",
  "org.webjars.npm" % "reflect-metadata" % "0.1.2",
  "org.webjars.npm" % "zone.js" % "0.6.6",
  "org.webjars.npm" % "typescript" % "1.8.10",
  "org.webjars.npm" % "tslint-eslint-rules" % "1.2.0",
  "org.webjars.npm" % "bootstrap" % "4.0.0-alpha.2",
  "org.webjars.npm" % "ng2-material" % "0.3.5",
  "org.webjars.npm" % "jasmine-core" % "2.4.1",
  "org.mockito" % "mockito-core" % "1.9.5" % "test",
  "org.webjars.npm" % "ng2-dragula" % "1.1.2",

  "mysql" % "mysql-connector-java" % "5.1.18"
)

libraryDependencies += "org.postgresql" % "postgresql" % "9.4-1201-jdbc41"

//libraryDependencies += "org.eluder.coveralls" % "coveralls-maven-plugin" % "4.2.0"

//libraryDependencies += "org.jacoco" % "jacoco-maven-plugin" % "0.7.6.201602180812"



import de.johoop.findbugs4sbt.FindBugs._

findbugsSettings

dependencyOverrides += "org.webjars.npm" % "minimatch" % "2.0.10"

// FindBugs
import de.johoop.findbugs4sbt.FindBugs._
import de.johoop.findbugs4sbt.ReportType

findbugsSettings
findbugsIncludeFilters := Some(<FindBugsFilter> // include controllers
  <Match>
    <Package name="controllers" />

    <Not>
      <Or>
        <Class name="controllers.routes" />
        <Class name="controllers.routes$javascript" />
      </Or>
    </Not>
  </Match>
  <Match>
    <Package name="models" />

    <Not>
      <Or>
        <Bug code="SnVI" />
        <Bug pattern="EI_EXPOSE_REP" />
        <Bug code="MS" />
        <Bug code="DLS" />
      </Or>
    </Not>
  </Match>
  <Match>
    <Package name="util" />
  </Match>
</FindBugsFilter>)
findbugsReportType := Some(ReportType.FancyHtml) // generate html
findbugsReportPath := Some(crossTarget.value / "findbugs" / "report.html") // create html extension so we can open it in our browser

compile <<= (compile in Compile).dependsOn(findbugs) // generate report on every compilation

// the typescript typing information is by convention in the typings directory
// It provides ES6 implementations. This is required when compiling to ES5.
typingsFile := Some(baseDirectory.value / "typings" / "browser.d.ts")

resolveFromWebjarsNodeModulesDir := true

//(rulesDirectories in tslint) := Some(List(tslintEslintRulesDir.value))

routesGenerator := InjectedRoutesGenerator

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )

// code coverage
jacoco.settings
parallelExecution in jacoco.Config := false

// Exclude generated classes, routes and views
jacoco.excludes in jacoco.Config := Seq("views*", "*Routes*", "controllers*routes*", "controllers*Reverse*", "controllers*javascript*", "controller*ref*")

coverageExcludedPackages := "<empty>;Routes*;controllers*routes*;controllers*Reverse*;controllers*javascript*;controller*ref*"
