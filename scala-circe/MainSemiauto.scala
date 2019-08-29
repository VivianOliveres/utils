import java.util.UUID

import io.circe.generic.semiauto._
import io.circe.Decoder.Result
import io.circe._
import io.circe.parser._
import io.circe.syntax._

object MainSemiauto extends SemiCodec {

  def main(args: Array[String]): Unit = {
    val redText: ColorText = ColorText(Red, "Red with 1")
    val greenText: ColorText = ColorText(Green, "Green with 3")
    val blueText: ColorText = ColorText(Blue, "Blue with 5")
    val description: Description = Description("Movie", Some("Movie description"))
    val colors: Map[Color, ColorText] = Map(Red -> redText, Green -> greenText, Blue -> blueText)
    val obj: Root = Root(UUID.randomUUID(), description, colors)

    // Encode as Json
    val json: Json = obj.asJson

    // Encode as Json String
    val jsonAsString: String = json.noSpaces
    println(jsonAsString)

    // Decode with as[T]
    val decodedResult: Result[Root] = json.as[Root]
    println(decodedResult)

    // Decode with decode[T]
    val decodedEither: Either[Error, Root] = decode[Root](jsonAsString)
    println(decodedEither)

    // Decode as option
    val decodedOption: Option[Root] = json.as[Root].toOption
    println(decodedOption)
  }
}

trait SemiCodec {
  sealed trait Color
  case object Red extends Color
  case object Blue extends Color
  case object Green extends Color
  implicit val colorDecoder: Decoder[Color] = deriveDecoder
  implicit val colorEncoder: Encoder[Color] = deriveEncoder
  implicit val colorKeyDecoder: KeyDecoder[Color] = new KeyDecoder[Color] {
    override def apply(key: String): Option[Color] = key match {
      case "Red" => Some(Red)
      case "Blue" => Some(Blue)
      case "Green" => Some(Green)
      case _ => None
    }
  }
  implicit val colorKeyEncoder: KeyEncoder[Color] = new KeyEncoder[Color] {
    override def apply(key: Color): String = key.toString
  }

  case class ColorText(color: Color, text: String)
  implicit val colorTextTextDecoder: Decoder[ColorText] = deriveDecoder
  implicit val colorTextTextEncoder: Encoder[ColorText] = deriveEncoder

  case class Description(category: String, text: Option[String])
  implicit val descriptionTextDecoder: Decoder[Description] = deriveDecoder
  implicit val descriptionTextEncoder: Encoder[Description] = deriveEncoder

  case class Root(id: UUID, description: Description, colors: Map[Color, ColorText])
  implicit val rootDecoder: Decoder[Root] = deriveDecoder
  implicit val rootEncoder: Encoder[Root] = deriveEncoder
}