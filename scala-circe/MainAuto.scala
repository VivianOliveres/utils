import io.circe.Decoder.Result
import io.circe._
import io.circe.generic.auto._
import io.circe.parser._
import io.circe.syntax._

object MainAuto {

  def main(args: Array[String]): Unit = {
    val obj: Something = Something("Toto", 13, Some(14.0), Set("red", "blue"), Map("movie" -> 123, "music" -> 456))

    // Encode as Json
    val json: Json = obj.asJson

    // Encode as Json String
    val jsonAsString: String = json.noSpaces
    println(jsonAsString)

    // Decode with as[T]
    val decodedResult: Result[Something] = json.as[Something]
    println(decodedResult)

    // Decode with decode[T]
    val decodedEither: Either[Error, Something] = decode[Something](jsonAsString)
    println(decodedEither)

    // Decode as option
    val decodedOption: Option[Something] = json.as[Something].toOption
    println(decodedOption)
  }
}

case class Something(name: String,
                     count: Int,
                     value: Option[Double],
                     attributes: Set[String],
                     categories: Map[String, Int])
