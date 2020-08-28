package com.oneq.user

import slick.jdbc.H2Profile.api._
import slick.lifted.{ProvenShape, ForeignKeyQuery}
import scala.concurrent.Future

case class User(id: Option[Int], email: String, password: String)

case class UserLogin(email: String, password: String)

trait UsersTable {
  class Users(tag: Tag) extends Table[User](tag, "USERS") {
    def id = column[Int]("ID", O.PrimaryKey, O.AutoInc)
    def email = column[String]("EMAIL", O.Unique)
    def password = column[String]("PASSWORD")

    def * = (id ?, email, password) <> (User.tupled, User.unapply)
  }

  val users = TableQuery[Users]
}
