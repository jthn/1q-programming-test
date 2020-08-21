package com.oneq.user

import spray.json._
import spray.json.DefaultJsonProtocol._

import scala.concurrent.Await
import scala.concurrent.duration.Duration
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Success, Failure}

import akka.actor.ActorSystem

class UserService(repo: UserRepository)(implicit system: ActorSystem) {
  def show(id: Int) = {}

  def getAll = {
    repo.list.onComplete {
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
