package com.oneq.user

import akka.http.scaladsl.server.Directives._
import scala.concurrent.ExecutionContext

import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._
import com.softwaremill.session._

import com.oneq.JsonSupport

import com.oneq.session._

class UserRoutes(repo: UserRepository)(implicit ex: ExecutionContext) extends JsonSupport {
  implicit val mgr = SessionData.manager

  implicit val service = new UserService(repo)

  val auth = (post & pathPrefix("login")) {
    entity(as[UserLogin]) { body =>
      println(s"payload: ${body}")

      service.login(body.email, body.password)

      val sessionData = new SessionData(body.email)

      setSession(oneOff, usingHeaders, sessionData) { ctx =>
        ctx.complete("OK")
      }
    }
  }

  val users = (pathPrefix("user")) {
    (pathEndOrSingleSlash) {
      get {
        complete(repo.list)
      } ~
        entity(as[User]) { body =>
          val users = service.create(body.email, body.password)

          println(users)

          complete("ok")
        }
    } ~ (path(IntNumber)) { id =>
      get {
        complete(repo.show(id))
      }
    }

  }

  val routes = users ~ auth
}
