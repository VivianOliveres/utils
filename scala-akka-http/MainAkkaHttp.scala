import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor
import scala.util.{Failure, Success}

object MainAkkaHttp extends CirceSupport {

  def main(args: Array[String]) {

    implicit val system = ActorSystem()
    implicit val materializer = ActorMaterializer()
    implicit val executionContext: ExecutionContextExecutor = system.dispatcher

    val versionRoutes = new VersionRoutes
    val userRoutes = new UserRoutes(new UserService)

    val routes = versionRoutes.routes ~ userRoutes.routes

    Http()
      .bindAndHandle(routes, "localhost", 8888)
      .onComplete {
        case Success(serverBinding) =>
          println(s"Api bound to ${serverBinding.localAddress}")
        case Failure(e) =>
          println(e, s"Failed to start")
      }
  }
}