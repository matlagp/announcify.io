package models

import java.util.UUID

case class Token(token_uuid: UUID, user: Model[User])

object Token {
  def generate(user: Model[User]) = Token(UUID.randomUUID(), user)
}