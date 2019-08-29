import java.util.UUID

import scala.concurrent.Future

object VersionService {

  case class ServiceVersion(version: String)

  def version: Future[ServiceVersion] =
    Future.successful(ServiceVersion(UUID.randomUUID().toString))

}
