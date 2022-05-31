libraryDependencies ++= List(
  "org.slf4j" % "slf4j-nop" % "1.7.26",
  "com.h2database" % "h2" % "1.4.200",
  "org.xerial" % "sqlite-jdbc" % "3.36.0.3"
)

scalacOptions += "-deprecation"

run / fork := true
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.4.0-M1"
