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
      version := dynverGitDescribeOutput.value.mkVersion(versionFmt, fallbackVersion(dynverCurrentDate.value)),
      dynver := {
        val d = new java.util.Date
        sbtdynver.DynVer.getGitDescribeOutput(d).mkVersion(versionFmt, fallbackVersion(d))
      },
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
publishTo in ThisBuild := Some(
  if (isSnapshot.value)
    Opts.resolver.sonatypeSnapshots
  else
    Opts.resolver.sonatypeStaging
)

def versionFmt(out: sbtdynver.GitDescribeOutput): String = {
  val prefix = out.ref.dropV.value
  val rev    = out.commitSuffix.mkString("+", "-", "")
  val dirty  = out.dirtySuffix.value

  val ver = (rev, dirty) match {
    case ("", "") =>
      prefix
    case (_, _) =>
      // (version)+(distance)-(rev)
      prefix + rev
  }
  val dynamicVersion = if (out.hasNoTags()) s"0.0.0-${out.version}" else ver
  val isSnapshot     = out.isSnapshot() || out.hasNoTags()
  if (isSnapshot) s"$dynamicVersion-SNAPSHOT" else dynamicVersion
}

def fallbackVersion(d: java.util.Date): String = s"HEAD-${sbtdynver.DynVer timestamp d}"

