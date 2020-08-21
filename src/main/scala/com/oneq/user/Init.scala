package com.oneq.user

import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global

object Init {
  val db = Database.forConfig("oneq.database")

  val users = TableQuery[Users]

  val setup = DBIO.seq(
    users.schema.create,
    users += ("test1@example.com", "123"),
    users += ("test2@example.com", "456")
  )

  def run: Future[Unit] = db.run(setup)
}
