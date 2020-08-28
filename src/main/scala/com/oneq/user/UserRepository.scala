package com.oneq.user

import slick.jdbc.H2Profile.api._

import scala.concurrent.{Future, Await, ExecutionContext}
import akka.actor.ActorSystem

class UserRepository(db: Database)(implicit system: ActorSystem) extends UsersTable {
  // loads ExecutionContextExecutor for use downstream
  implicit val dispatch: ExecutionContext = system.getDispatcher

  def show(id: Int) =
    db.run {
      users.filter(_.id === id).result
    }

  def list =
    db.run {
      users.result
    }

  def create(email: String, password: String) =
    db.run {
      (users returning users.map(_.id)) += User(None, email, password)
    }

  def byLogin(email: String, password: String): Future[Unit] = {
    val q = DBIO.seq(sql"""
      select * from USERS where email = $email and password = $password
    """.as[(String, String)])

    db.run(q).map { response =>
      println(response.toString)
    }
  }

  def prepareDB() = {
    println("prepare db")

    val a = for {
      _ <- users.schema.createIfNotExists
      r1 <- users.insertOrUpdate(User(Some(1), "test1@example.com", "pass1"))
      r2 <- users.insertOrUpdate(User(Some(2), "test2@example.com", "pass2"))
    } yield r1 + r2

    db.run(a)
  }
}
