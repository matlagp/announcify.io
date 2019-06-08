name := "announcify"
 
version := "1.0" 
      
lazy val `announcify` = (project in file(".")).enablePlugins(PlayScala)

resolvers += "scalaz-bintray" at "https://dl.bintray.com/scalaz/releases"
      
resolvers += "Akka Snapshot Repository" at "http://repo.akka.io/snapshots/"
      
scalaVersion := "2.12.2"

libraryDependencies ++= Seq( ehcache , ws , specs2 % Test , guice,"com.typesafe.play" %% "play-slick" % "3.0.0",
"com.typesafe.play" %% "play-slick-evolutions" % "3.0.0","org.postgresql" % "postgresql" % "9.4-1201-jdbc41")

unmanagedResourceDirectories in Test <+=  baseDirectory ( _ /"target/web/public/test" )  

      