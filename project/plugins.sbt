logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"
resolvers += DefaultMavenRepository

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.0")

addSbtPlugin("name.de-vries" % "sbt-typescript" % "0.2.4")

addSbtPlugin("name.de-vries" % "sbt-tslint" % "0.9.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "3.0.0")

addSbtPlugin("org.scoverage" % "sbt-scoverage" % "1.3.5")

addSbtPlugin("org.irundaia.sbt" % "sbt-sassify" % "1.4.5")

addSbtPlugin("com.etsy" % "sbt-checkstyle-plugin" % "3.0.0")

addSbtPlugin("de.johoop" % "findbugs4sbt" % "1.4.0")

addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.6")
