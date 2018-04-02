organization in ThisBuild := "com.example"
version in ThisBuild := "1.0-SNAPSHOT"

// the Scala version that will be used for cross-compiled libraries
scalaVersion in ThisBuild := "2.12.4"

lazy val `lagom-unit-and-component-testing` = (project in file("."))
  .aggregate(
    `user-api`, `user-impl`, `external-api`)

lazy val `external-api` = (project in file("external-api"))
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lagomLogback,
      lombok
    )

  )

lazy val `user-api` = (project in file("user-api"))
  .settings(common: _*)
  .settings(
    libraryDependencies ++= Seq(
      lagomJavadslApi,
      lombok
    )
  )
def jmockitPath(f: Seq[File]) = f.filter(_.name.endsWith("jmockit-1.7.jar")).head

lazy val `user-impl` = (project in file("user-impl"))
  .enablePlugins(LagomJava)
  .settings(common: _*)
  .settings(

    libraryDependencies ++= Seq(
      "com.novocode" % "junit-interface" % "0.9" % "test",
      "com.googlecode.jmockit" % "jmockit" % "1.7" % "test",
      "junit" % "junit" % "4.8.1" % "test",
      lagomJavadslTestKit
    ),
    javaOptions in Test += s"-javaagent:${jmockitPath((dependencyClasspath in Test).value.files)}"

  )
  .settings(lagomForkedTestSettings: _*)
  .dependsOn(`user-api`, `external-api`)

val lombok = "org.projectlombok" % "lombok" % "1.16.18"
val jmockit = "org.jmockit" % "jmockit" % "1.8" % Test
val junit = "junit" % "junit" % "4.12" % Test
val junitInterface = "com.novocode" % "junit-interface" % "0.11" % Test

def common = Seq(
  javacOptions in compile += "-parameters"
)
crossPaths := false
lagomUnmanagedServices in ThisBuild := Map("external-service" -> "https://jsonplaceholder.typicode.com:443")
lagomKafkaEnabled in ThisBuild := false
lagomCassandraEnabled in ThisBuild := false
fork in Test := true
