import AssemblyKeys._

assemblySettings

resolvers += "sonatype-public" at "https://oss.sonatype.org/content/groups/public"

jarName in assembly := "hw7.jar"

excludedJars in assembly <<= (fullClasspath in assembly) map { cp => 
  cp filter {x => Seq("jtransforms-2.4.0.jar", "jplasma-1.2.0.jar") contains x.data.getName }
}

name := "hw7"

version := "1.0"

scalaVersion := "2.10.0"

libraryDependencies += "net.sourceforge.parallelcolt" % "parallelcolt" % "0.10.0"

libraryDependencies ++= Seq(
   "junit" % "junit" % "4.8.1" % "test"
   )
