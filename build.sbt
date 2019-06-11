name := "announcify"
 
version := "1.0"

val swagger = ("io.swagger" %% "swagger-play2" % "1.6.0") excludeAll (ExclusionRule("io.swagger", "swagger-scala-module"), ExclusionRule("io.swagger", "swagger-annotations"))
val swaggerScalaModule = ("io.swagger" %% "swagger-scala-module" % "1.0.4") exclude("com.fasterxml.jackson.module", "jackson-module-scala")
val swaggerAnnotation = "io.swagger" % "swagger-annotations" % "1.5.20"
val jacksonModuleScala = "com.fasterxml.jackson.module" %% "jackson-module-scala" % "2.9.4"
val swaggerUI = "org.webjars" % "swagger-ui" % "3.17.6"

libraryDependencies ++= Seq(swagger)
libraryDependencies ++= Seq(swaggerAnnotation)
libraryDependencies ++= Seq(swaggerScalaModule)
libraryDependencies ++= Seq(jacksonModuleScala)
libraryDependencies ++= Seq(swaggerUI)

lazy val `announcify` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies += guice
libraryDependencies += "org.scalatestplus.play" %% "scalatestplus-play" % "4.0.2" % Test
libraryDependencies += "com.typesafe.play" %% "play-slick" % "4.0.0"
libraryDependencies += "com.typesafe.play" %% "play-slick-evolutions" % "4.0.0"
libraryDependencies += "org.postgresql" % "postgresql" % "42.1.4"
libraryDependencies += specs2 % Test
libraryDependencies += ws

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  
