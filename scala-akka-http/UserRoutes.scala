import akka.actor.ActorSystem
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import io.circe.generic.auto._

class UserRoutes(userService: UserService)(implicit system: ActorSystem, materializer: ActorMaterializer)
  extends CirceSupport {

  private val getUsersRoute =
    get {
      path("users") {
        parameters("age".as[Int] ?) { maybeAge =>
          val eventualResult = maybeAge.fold(userService.users)(userService.user)
          onSuccess(eventualResult)(complete(_))
        }
      }
    }

  private val getUserByNameRoute =
    get {
      path("users" / Segment) { name =>
        val eventualResult = userService.user(name)
        onSuccess(eventualResult) (complete(_))
      }
    }

  private val postUserRoute =
    post {
      path("users") {
        entity(as[User]) { user =>
          val eventualResult = userService.addUser(user)
          onSuccess(eventualResult) (complete(_))
        }
      }
    }

  private val deleteUserRoute =
    delete {
      path("users" / Segment) { name =>
        val eventualResult = userService.deleteUser(name)
        onSuccess(eventualResult) (complete(_))
      }
    }

  private val putUserRoute =
    put {
      path("users") {
        entity(as[User]) { user =>
          val eventualResult = userService.updateUser(user)
          onSuccess(eventualResult) (complete(_))
        }
      }
    }

  val routes = getUsersRoute ~ getUserByNameRoute ~ postUserRoute ~ deleteUserRoute ~ putUserRoute
}
