package com.oneq.user

import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global

object Repo {
  val db = Database.forConfig("oneq.database")

  val users = TableQuery[Users]

  def getAll = {
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
}
