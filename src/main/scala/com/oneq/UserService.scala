package com.oneq

import spray.json._
import spray.json.DefaultJsonProtocol._

final case class User(id: Int, email: String)

final case class Users(users: Seq[User])

object UserService {
  def show(id: Int) = {
    User(id = 14, email = "test@example.com")
  }
}
