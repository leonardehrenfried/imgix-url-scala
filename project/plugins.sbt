val scalaJSVersion = Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.21")

addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)

addSbtPlugin("com.github.gseitz" % "sbt-release" % "1.0.6")

addSbtPlugin("com.lucidchart" % "sbt-scalafmt-coursier" % "1.14")

addSbtPlugin("org.xerial.sbt" % "sbt-sonatype" % "2.0")

addSbtPlugin("com.jsuereth" % "sbt-pgp" % "1.1.0")
