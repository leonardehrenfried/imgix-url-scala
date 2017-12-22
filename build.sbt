import ReleaseTransformations._
import sbtcrossproject.{crossProject, CrossType}

lazy val `imgix-url` =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .settings(
      name := "imgix-url",
      organization := "io.leonard",
      scalaVersion := "2.12.4",
      // POM settings for Sonatype
      homepage := Some(url("https://github.com/leonardehrenfried/imgix-url-scala")),
      scmInfo := Some(ScmInfo(url("https://github.com/leonardehrenfried/imgix-url-scala"),
                              "git@github.com:leonardehrenfried/imgix-url-scala.git")),
      developers := List(
        Developer("leonardehrenfried",
                  "Leonard Ehrenfried",
                  "mail@leonard.io",
                  url("https://github.com/leonardehrenfried"))),
      licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
      publishMavenStyle := true,
      releasePublishArtifactsAction := PgpKeys.publishSigned.value // Use publishSigned in publishArtifacts step
    )
    .jsSettings()
    .jvmSettings()

lazy val `img-url-js`    = `imgix-url`.js
lazy val `imgix-url-jvm` = `imgix-url`.jvm

// disable publishing for root project, js and jvm are the actual artifacts
publish := {}
publishLocal := {}

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  publishArtifacts,
  setNextVersion,
  commitNextVersion,
  releaseStepCommand("sonatypeReleaseAll"),
  pushChanges
)

// defaults
releaseTagComment := s"Release ${(version in ThisBuild).value}"
releaseCommitMessage := s"Set version to ${(version in ThisBuild).value}"

fork := false

// Add sonatype repository settings
publishTo in ThisBuild := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)
