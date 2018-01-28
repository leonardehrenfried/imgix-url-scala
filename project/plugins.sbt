val scalaJSVersion = Option(System.getenv("SCALAJS_VERSION")).getOrElse("0.6.22")
addSbtPlugin("org.scala-js" % "sbt-scalajs" % scalaJSVersion)

addSbtPlugin("org.portable-scala" % "sbt-crossproject"         % "0.3.0")
addSbtPlugin("org.portable-scala" % "sbt-scalajs-crossproject" % "0.3.0")

addSbtPlugin("ch.epfl.scala"     % "sbt-release-early" % "2.0.1")
addSbtPlugin("org.xerial.sbt"    % "sbt-sonatype" % "2.1")
addSbtPlugin("com.jsuereth"      % "sbt-pgp"      % "1.1.0")

addSbtPlugin("com.lucidchart" % "sbt-scalafmt-coursier" % "1.15")
