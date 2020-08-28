package com.oneq.user

import spray.json._
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext
import scala.util.{Success, Failure}

import akka.actor.ActorSystem

class UserService(repo: UserRepository)(implicit ec: ExecutionContext) {
  def show(id: Int) = {
    repo.show(id).onComplete {
      case Success(value) => value
      case Failure(e)     => println(e)
    }
  }

  def getAll = {
    repo.list.onComplete {
      case Success(value) => {
        println(s"success: ${value}")
      }
      case Failure(error) => {
        println(s"fail ${error}")
      }
    }
  }

  def create(email: String, password: String) = {
    repo.create(email, password).onComplete {
      case Success(value) => {
        println(s"success: ${value}")
      }
      case Failure(e) => println(e)
    }
  }

  def login(email: String, password: String) = {
    repo.byLogin(email, password).onComplete {
      case Success(value) => {
        println("success")
        println(value.toString)
      }
      case Failure(e: NoSuchElementException) => {
        println("user/pass not found")
      }
      case Failure(e) => {
        println(e.toString)
      }
    }
  }
}
