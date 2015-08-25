organization := "st.process"

name := "squala"

version := "0.1.0-SNAPSHOT"

scalaVersion := "2.11.7"

libraryDependencies += "org.scalatest" % "scalatest_2.11" % "2.2.4" % "test"

// To publish to Sonatype
// Reference: http://www.scala-sbt.org/0.13/docs/Using-Sonatype.html

//publishMavenStyle := true
//
//publishTo := {
//    val nexus = "https://oss.sonatype.org/"
//    if (isSnapshot.value)
//        Some("snapshots" at nexus + "content/repositories/snapshots")
//    else
//        Some("releases"  at nexus + "service/local/staging/deploy/maven2")
//}
//
//publishArtifact in Test := false
//
//pomIncludeRepository := { _ => false }
//
//pomExtra :=
//    <url>https://github.com/process-street/squala</url>
//    <licenses>
//        <license>
//            <name>MIT</name>
//            <url>http://opensource.org/licenses/MIT</url>
//            <distribution>repo</distribution>
//        </license>
//    </licenses>
//    <scm>
//        <url>git@github.com:process-street/squala.git</url>
//        <connection>scm:git:git@github.com:process-street/squala.git</connection>
//    </scm>
//    <developers>
//        <developer>
//            <id>cdmckay</id>
//            <name>Cameron McKay</name>
//            <email>cameron@process.st</email>
//        </developer>
//    </developers>
//
//credentials += Credentials(
//    "Sonatype Nexus Repository Manager",
//    "oss.sonatype.org",
//    "<your username>",
//    "<your password>"
//)

