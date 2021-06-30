package com.danielasfregola.twitter4s.http.serializers

import com.danielasfregola.twitter4s.entities.v2.Place
import org.json4s.native.Serialization
import org.specs2.mutable.Specification

class PlaceV2SerDeSpec extends Specification with JsonSupport {

  "JSON deserialization of v2.Place" should {

    "map place.geo to string if exists" in {
      val json = """{
                   |  "full_name": "Two-Bit's Retro Arcade",
                   |  "geo": {
                   |    "type": "Feature",
                   |    "bbox": [
                   |      -73.9872138639517,
                   |      40.72083090861322,
                   |      -73.9872138639517,
                   |      40.72083090861322
                   |    ],
                   |    "properties": {}
                   |  },
                   |  "id": "0fc2bbe1f995b000"
                   |}""".stripMargin

      val deserialized = Serialization.read[Place](json)

      println(deserialized.id)
      println(deserialized.full_name)
      println(deserialized.geo)

      deserialized.id === "0fc2bbe1f995b000"
      deserialized.full_name === "Two-Bit's Retro Arcade"
      deserialized.geo === Some("{\"type\":\"Feature\",\"bbox\":[-73.9872138639517,40.72083090861322,-73.9872138639517,40.72083090861322],\"properties\":{}}")
    }

  }

}
