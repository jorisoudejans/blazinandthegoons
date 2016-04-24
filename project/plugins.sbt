logLevel := Level.Warn

resolvers += "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/"

addSbtPlugin("com.typesafe.play" % "sbt-plugin" % "2.5.0")

addSbtPlugin("name.de-vries" % "sbt-typescript" % "0.2.4")

//addSbtPlugin("name.de-vries" % "sbt-tslint" % "0.9.4")

addSbtPlugin("com.typesafe.sbt" % "sbt-play-ebean" % "3.0.0")