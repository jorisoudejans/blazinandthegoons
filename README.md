# Blazin and the Goons
Contextproject MMS

## Setup instructions
The following section describes command to run various things

### Simple test
`sbt test`

### Tests with coverage
As a code coverage tool, the project uses JaCoCo to get a coverage report together with the jacoco4sbt plugin. In order to run it, use `sbt jacoco:cover`
The report can be found at {root}/target/scala-2.11/jacoco/index.html

## Findbugs
In order to generate a report, execute
`sbt findbugs`

The report will be in `{$project-root}/target/scala-2.11/findbugs/report.html`

The configuration includes all java classes and ignores bugs created by the Play framework implementation (mostly Ebean)
