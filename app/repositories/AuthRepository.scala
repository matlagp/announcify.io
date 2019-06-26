package repositories

import javax.inject.{Inject, Singleton}
import models.{Model, Token, User}
import play.api.db.slick.DatabaseConfigProvider

import scala.concurrent.{ExecutionContext, Future}

@Singleton
class AuthRepository @Inject()
  (dbConfigProvider: DatabaseConfigProvider)
  (implicit ec: ExecutionContext) {{

  }
//  private def insert(token: Token): Future[Int] = Future.unit

//  def createToken(user: Model[User]): Future[Model[Token]] = {
//    val token = Token.generate(user)
//    insert(token).map(id => Model[Token](id, token))
//  }
//
//  def getUserByToken(token: StringContext): Future[Option[Model[User]]] = {
//
//  }
}
