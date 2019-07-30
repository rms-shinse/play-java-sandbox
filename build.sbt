name := """play-java-seed"""
organization := "jp.co.sensemaking"

version := "1.0-SNAPSHOT"

lazy val root = (project in file("."))
  .enablePlugins(PlayJava, PlayEbean)

scalaVersion := "2.11.7"

libraryDependencies ++= Seq(
  guice,
  "com.h2database" % "h2" % "1.4.192",
  "org.assertj" % "assertj-core" % "3.12.2"
)