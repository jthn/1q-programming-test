package com.oneq

import spray.json._
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

import com.oneq.user._

final case class User(id: Int, email: String)

final case class Users(users: Seq[User])

object UserService {
  def show(id: Int) = {
    User(id = 14, email = "test@example.com")
  }

  def getAll = {
    Repo.getAll.onComplete {
      case Success(value) => {
        println("success")
        println(value.toString)
      }
      case Failure(error) => {
        println("fail")
        println(error.toString)
      }
    }
  }

  def login(email: String, password: String) = {
    try {
      Await.result(Repo.byLogin(email, password), Duration.Inf)
    } catch {
      case e: NoSuchElementException => println("invalid login")
    }
  }
}
