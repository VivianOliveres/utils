import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import io.circe.generic.auto._

import scala.concurrent.Future

class VersionRoutes(implicit system: ActorSystem, materializer: ActorMaterializer)
  extends CirceSupport {

  private val getVersionRoute =
    path("version") {
      get {
        val maybeResult: Future[VersionService.ServiceVersion] = VersionService.version
        onSuccess(maybeResult) (complete(_))
      }
    }

  val routes = getVersionRoute
}
