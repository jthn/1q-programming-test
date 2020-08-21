package com.oneq.user

import slick.jdbc.H2Profile.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}

case class User(email: String, password: String)

trait UsersTable {
  class Users(tag: Tag) extends Table[(String, String)](tag, "users") {
    def email: Rep[String] = column[String]("EMAIL")
    def password: Rep[String] = column[String]("PASSWORD")

    def * : ProvenShape[(String, String)] =
      (email, password)
  }

  val users = TableQuery[Users]
}
