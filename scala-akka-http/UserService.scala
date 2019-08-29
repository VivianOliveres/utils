import scala.concurrent.Future

class UserService {

  private var state: List[User] = List(User("toto", 18), User("titi", 21))

  def users: Future[List[User]] =
    Future.successful(state)

  def user(name: String): Future[Option[User]] =
    Future.successful(state.find(_.name.equalsIgnoreCase(name)))

  def user(age: Int): Future[List[User]] =
    Future.successful(state.filter(_.age == age))

  def addUser(user: User): Future[User] =
    Future.successful({
      state = state :+ user
      user
    })

  def deleteUser(name: String): Future[Option[User]] =
    Future.successful({
      val maybeUserToDelete = state.find(_.name.equalsIgnoreCase(name))
      state = maybeUserToDelete.fold(state) (userToRemove => state.filterNot(_ == userToRemove))
      maybeUserToDelete
    })

  def updateUser(user: User): Future[User] =
    Future.successful({
      val maybeUserToDelete = state.find(_.name.equalsIgnoreCase(user.name))
      state = maybeUserToDelete.fold(state) (userToRemove => state.filterNot(_ == userToRemove)) :+ user
      user
    })
}

case class User(name: String, age: Int)
