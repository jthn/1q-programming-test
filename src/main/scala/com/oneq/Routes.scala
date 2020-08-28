package com.oneq

import com.typesafe.config._

import akka.http.scaladsl.model._
import akka.http.scaladsl.server.{Directives, Route}
import akka.http.scaladsl.model.StatusCodes.Redirection

import com.softwaremill.session.SessionDirectives._
import com.softwaremill.session.SessionOptions._
import com.softwaremill.session._

import com.oneq.session._
import com.oneq.user._

object AllRoutes extends Directives with JsonSupport {
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

  val root = get { // Default to index page
    redirect("/static/", StatusCodes.TemporaryRedirect)
  }

  def routes: Route = health ~ static ~ root
}
