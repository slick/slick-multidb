libraryDependencies ++= List(
  "org.slf4j" % "slf4j-nop" % "1.7.36",
  "com.h2database" % "h2" % "2.1.214",
  "org.xerial" % "sqlite-jdbc" % "3.36.0.3"
)

scalacOptions += "-deprecation"

run / fork := true
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.4.0-M1"
