import _root_.io.github.nafg.mergify.dsl.*

ThisBuild / scalaVersion := "2.13.15"
ThisBuild / scalacOptions += "-Xsource:3"

mergifyExtraConditions := Seq(
  (Attr.Author :== "scala-steward") ||
    (Attr.Author :== "slick-scala-steward[bot]") ||
    (Attr.Author :== "renovate[bot]")
)

libraryDependencies ++= List(
  "org.slf4j"      % "slf4j-nop"   % "2.0.17",
  "com.h2database" % "h2"          % "2.4.240",
  "org.xerial"     % "sqlite-jdbc" % "3.51.1.0"
)

scalacOptions += "-deprecation"

run / fork                                  := true
libraryDependencies += "com.typesafe.slick" %% "slick" % "3.6.1"

// based on https://stackoverflow.com/a/63780833/333643
lazy val runAll = taskKey[Unit]("Run all main classes")

def runAllIn(config: Configuration) = Def.task {
  val s       = streams.value
  val cp      = (config / fullClasspath).value
  val r       = (config / run / runner).value
  val classes = (config / discoveredMainClasses).value
  classes.foreach { className =>
    r.run(className, cp.files, Seq(), s.log).get
  }
}

runAll := {
  runAllIn(Compile).value
  runAllIn(Test).value
}

ThisBuild / githubWorkflowJavaVersions          := Seq(JavaSpec.zulu("11"))
ThisBuild / githubWorkflowPublishTargetBranches := Seq()
ThisBuild / githubWorkflowBuild += WorkflowStep.Sbt(
  List("runAll"),
  name = Some(s"Run all main classes")
)
