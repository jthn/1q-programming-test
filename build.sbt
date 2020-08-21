name := "1Q-programming-test"
version := "0.0.1"

// AKKA DOCS
val AkkaVersion = "2.6.8"
val AkkaHttpVersion = "10.2.0"
val AkkaHttpSessionVersion = "0.5.11"
val Json4sVersion = "3.6.6"
val SlickVersion = "3.3.1"

lazy val root = (project in file(".")).settings(
  inThisBuild(
    List(
      organization := "com.oneq",
      scalaVersion := "2.12.12"
    )
  ),
  name := "$name$",
  libraryDependencies ++= Seq(
    "ch.qos.logback" % "logback-classic" % "1.2.3",
    "com.softwaremill.akka-http-session" %% "core" % AkkaHttpSessionVersion,
    "com.softwaremill.akka-http-session" %% "jwt" % AkkaHttpSessionVersion,
    "com.typesafe.akka" %% "akka-actor-testkit-typed" % AkkaVersion % Test,
    "com.typesafe.akka" %% "akka-http" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-spray-json" % AkkaHttpVersion,
    "com.typesafe.akka" %% "akka-http-testkit" % AkkaHttpVersion % Test,
    "com.typesafe.akka" %% "akka-stream" % AkkaVersion,
    "com.typesafe.slick" %% "slick" % SlickVersion,
    "com.typesafe.slick" %% "slick-hikaricp" % SlickVersion,
    "org.json4s" %% "json4s-ext" % Json4sVersion,
    "org.json4s" %% "json4s-jackson" % Json4sVersion,
    "org.json4s" %% "json4s-native" % Json4sVersion,
    "org.scalatest" %% "scalatest" % "3.0.8" % Test,
    "com.h2database" % "h2" % "1.4.200"
  )
)
