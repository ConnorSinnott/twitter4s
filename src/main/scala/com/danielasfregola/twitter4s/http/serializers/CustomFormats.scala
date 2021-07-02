package com.danielasfregola.twitter4s.http.serializers

import java.time._

import com.danielasfregola.twitter4s.entities.ProfileImage
import com.danielasfregola.twitter4s.entities.enums.DisconnectionCode
import com.danielasfregola.twitter4s.entities.enums.DisconnectionCode.DisconnectionCode
import com.danielasfregola.twitter4s.entities.v2.Place
import org.json4s.JsonAST.{JInt, JLong, JNull, JString}
import org.json4s.native.JsonMethods._
import org.json4s.{CustomSerializer, Extraction, Formats, JObject}

private[twitter4s] object CustomFormats extends FormatsComposer {

  override def compose(f: Formats): Formats =
    f + InstantSerializer + LocalDateSerializer + DisconnectionCodeSerializer + ProfileImageSerializer + ZonedDateTimeSerializer + PlaceV2Serializer

}

private[twitter4s] case object InstantSerializer
    extends CustomSerializer[Instant](format =>
      ({
        case JInt(i)                                            => DateTimeFormatter.parseInstant(i.toLong)
        case JLong(l)                                           => DateTimeFormatter.parseInstant(l)
        case JString(s) if DateTimeFormatter.canParseInstant(s) => DateTimeFormatter.parseInstant(s)
        case JString(stringAsUnixTime) if stringAsUnixTime.forall(_.isDigit) =>
          Instant.ofEpochMilli(stringAsUnixTime.toLong)
      }, {
        case instant: Instant => JString(DateTimeFormatter.formatInstant(instant))
      }))

private[twitter4s] case object LocalDateSerializer
    extends CustomSerializer[LocalDate](format =>
      ({
        case JString(dateString) =>
          dateString.split("-") match {
            case Array(year, month, date) => LocalDate.of(year.toInt, month.toInt, date.toInt)
            case _                        => null
          }
        case JNull => null
      }, {
        case date: LocalDate => JString(date.toString)
      }))

private[twitter4s] case object ZonedDateTimeSerializer
    extends CustomSerializer[ZonedDateTime](_ =>
      ({
        case JString(dateString) => DateTimeFormatter.parseZonedDateTime(dateString)
        case JNull               => null
      }, {
        case date: ZonedDateTime => JString(date.toString)
      }))

private[twitter4s] case object DisconnectionCodeSerializer
    extends CustomSerializer[DisconnectionCode](format =>
      ({
        case JInt(n) => DisconnectionCode(n.toInt)
        case JNull   => null
      }, {
        case code: DisconnectionCode => JInt(code.id)
      }))

private[twitter4s] case object ProfileImageSerializer
    extends CustomSerializer[ProfileImage](format =>
      ({
        case JString(n) => ProfileImage(n)
        case JNull      => null
      }, {
        case img: ProfileImage => JString(img.normal)
      })
    )

/**
  * Twitter provides the `geo` field of `Place` formatted to the
  * <a href="https://geojson.org/">GeoJSON</a> specification. Rather
  * than introducing another dependency to deserialize this field,
  * we'll map it as a String so that consumers can deserialize it
  * using the library of their choice.
  */
private[twitter4s] case object PlaceV2Serializer
  extends CustomSerializer[Place](_ =>
    ({
      case jsonObj =>
        implicit val format = org.json4s.DefaultFormats
        jsonObj.transformField {
          case ("geo", geo@JObject(_)) => ("geo", JString(compact(render(geo))))
        }.extract[Place]
    }, {
      case place: Place =>
        implicit val format = org.json4s.DefaultFormats
        Extraction.decompose(place)
    })
  )
