package com.oneq

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.model.StatusCodes.Redirection

object AllRoutes {
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
          complete(s"Received: ${id}")
        }
      }

  }

  val root = get { // Default to index page
    redirect("/static/", StatusCodes.TemporaryRedirect)
  }

  def routes = health ~ static ~ users ~ root
}

