package repositories

import javax.inject.Singleton
import models.{AnnouncementTag, Model, User}
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class UserRepository
(dbConfigProvider: DatabaseConfigProvider)
(implicit ec: ExecutionContext) {
  private val dbConfig = dbConfigProvider.get[JdbcProfile]

  import dbConfig._
  import profile.api._

  private class UsersTable(tag: Tag) extends Table[Model[User]](tag, "users") {
    def id = column[Long]("user_id", O.PrimaryKey, O.AutoInc)
    def oauth_id = column[String]("oauth_id", O.Unique)

    def * = (id, oauth_id) <> (
      i => Model(i._1, User(i._2)),
      (m: Model[User]) => Some(m.id, m.obj.oauth_id)
    )
  }

  private val users = TableQuery[UsersTable]
}
