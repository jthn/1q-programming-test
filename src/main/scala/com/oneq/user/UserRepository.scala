package com.oneq.user

import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global

class UserRepository(db: Database) extends UsersTable {
  def list = {
    val q = DBIO.seq(sql"""
      select * from users
    """.as[(String, String)])

    db.run(q)
  }

  def byLogin(email: String, password: String) = {
    val q = DBIO.seq(sql"""
      select * from users where email = $email and password = $password
    """.as[(String, String)])

    db.run(q).map { response =>
      println(response.toString)
    }
  }

  def prepareDB() = {
    val setup = DBIO.seq(
      users.schema.create,
      users += ("test1@example.com", "123"),
      users += ("test2@example.com", "456")
    )

    db.run(setup)
  }
}
