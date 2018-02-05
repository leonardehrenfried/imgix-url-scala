import sbtcrossproject.{crossProject, CrossType}
import xerial.sbt.Sonatype._

lazy val `imgix-url` =
  crossProject(JSPlatform, JVMPlatform)
    .crossType(CrossType.Pure)
    .settings(
      name := "imgix-url",
      organization := "io.leonard",
      scalaVersion := "2.12.4",
      // POM settings for Sonatype
      sonatypeProjectHosting := Some(GithubHosting("leonardehrenfried", "imgix-url-scala", "mail@leonard.io")),
      licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0")),
      publishMavenStyle := true
    )
    .jsSettings()
    .jvmSettings()

lazy val `img-url-js`    = `imgix-url`.js
lazy val `imgix-url-jvm` = `imgix-url`.jvm

// disable publishing for root project, js and jvm are the actual artifacts
publish := {}
publishLocal := {}

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
  if(out.isSnapshot) {
    val suffix = out.commitSuffix.mkString("-", "-", "") + out.dirtySuffix.dropPlus.mkString("-", "")
    prefix + suffix + "-SNAPSHOT"
  }
  else {
    prefix
  }
}

def fallbackVersion(d: java.util.Date): String = s"HEAD-${sbtdynver.DynVer timestamp d}"

inThisBuild(List(
  version := dynverGitDescribeOutput.value.mkVersion(versionFmt, fallbackVersion(dynverCurrentDate.value)),
  dynver := {
    val d = new java.util.Date
    sbtdynver.DynVer.getGitDescribeOutput(d).mkVersion(versionFmt, fallbackVersion(d))
  },
  //useGpg := true,
  pgpPassphrase := sys.env.get("PGP_PASSPHRASE").map(_.toArray)
))

inScope(Global)(
  Seq(
    credentials ++= (for {
      username <- sys.env.get("SONATYPE_USER")
      password <- sys.env.get("SONATYPE_PASSWORD")
    } yield
      Credentials(
        "Sonatype Nexus Repository Manager",
        "oss.sonatype.org",
        username,
        password
      )).toSeq
  )
)
