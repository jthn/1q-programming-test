package com.oneq

import com.typesafe.config._

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives
import akka.http.scaladsl.model.StatusCodes.Redirection
import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport

import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._
import com.softwaremill.session._

import com.oneq.session._

import spray.json._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val userFormat = jsonFormat2(User)
}

object AllRoutes extends Directives with JsonSupport {
  implicit val manager = new SessionManager[SessionData](SessionConfig.fromConfig())

  val auth = (post & pathPrefix("login")) {
    entity(as[String]) { body =>
      val sessionData = new SessionData(body)

      setSession(oneOff, usingHeaders, sessionData) { ctx =>
        ctx.complete("OK")
      }
    }
  }

  val health = (get & path("health")) {
    complete(HttpEntity(ContentTypes.`text/plain(UTF-8)`, "OK"))
  }

  val static = (get & pathPrefix("static")) {
    (pathEndOrSingleSlash & redirectToTrailingSlashIfMissing(
      StatusCodes.TemporaryRedirect
    )) {
      getFromResource("static/index.html")
    } ~ {
      getFromResourceDirectory("static")
    }
  }

  val users = (get & pathPrefix("user")) {
    (pathEndOrSingleSlash) {
      complete(
        HttpEntity(ContentTypes.`text/plain(UTF-8)`, "All Users [admin only]")
      )
    } ~
      (path(IntNumber)) { id =>
        get {
          complete(UserService.show(id).toJson)
        }
      }

  }

  val root = get { // Default to index page
    redirect("/static/", StatusCodes.TemporaryRedirect)
  }

  def routes = auth ~ health ~ static ~ users ~ root
}
