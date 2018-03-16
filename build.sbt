import xerial.sbt.Sonatype._
import sbtcrossproject.{crossProject, CrossType}

lazy val `imgix-url` =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .settings(
      name := "imgix-url",
      organization := "io.leonard",
      scalaVersion := "2.12.4",
      // POM settings for Sonatype
      sonatypeProjectHosting := Some(GitHubHosting("leonardehrenfried", "imgix-url-scala", "mail@leonard.io")),
      licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
      publishMavenStyle := true,
      //useGpg := true,
      pgpPassphrase := sys.env.get("PGP_PASSPHRASE").map(_.toArray)
    )
    .jsSettings()
    .jvmSettings(
      publishArtifact := !scalaJSVersion.startsWith("1")
    )

lazy val `img-url-js`    = `imgix-url`.js
lazy val `imgix-url-jvm` = `imgix-url`.jvm

// disable publishing for root project, js and jvm are the actual artifacts
organization := "io.leonard"
publish := {}
publishLocal := {}
publishArtifact := false

fork := false

// Add sonatype repository settings
publishTo in ThisBuild := sonatypePublishTo.value
dynverSonatypeSnapshots in ThisBuild := true

