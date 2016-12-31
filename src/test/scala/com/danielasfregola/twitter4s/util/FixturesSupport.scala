package com.danielasfregola.twitter4s.util

import com.danielasfregola.twitter4s.http.serializers.JsonSupport
import org.json4s.native.Serialization

import scala.io.Source

trait FixturesSupport extends JsonSupport {

  def load(resourcePath: String): String = Source.fromURL(getClass.getResource(resourcePath)).mkString
  def loadJsonAs[T: Manifest](resourcePath: String): T = readJsonAs[T](load(resourcePath))

  def readJsonAs[T: Manifest](json: String): T = Serialization.read[T](json)
  def printAsJson[T <: AnyRef](value: T): Unit = println(Serialization.writePretty(value))
}

