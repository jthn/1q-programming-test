package com.oneq.user

import slick.jdbc.SQLiteProfile.api._

import scala.concurrent.{Future, Await}
import scala.concurrent.ExecutionContext.Implicits.global

object Init {
  val db = Database.forConfig("oneq.database")

  private def createUserTableSQL: DBIO[Int] =
    sqlu"""create table if not exists users(
      email varchar,
      passwordHash varchar,
      primary key (email)
    )"""

  def run: Future[Unit] =
    try {
      db.run(
        DBIO.seq(
          createUserTableSQL
        )
      )
    } finally db.close
}
