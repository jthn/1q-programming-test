package com.oneq

import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Route

// provides ~ operator to Route
import akka.http.scaladsl.server.Directives._

import slick.jdbc.SQLiteProfile.api._

import scala.util.Failure
import scala.util.Success
import akka.actor.ActorSystem

import com.oneq.user._

object App {
  private def startHttpServer(routes: Route)(implicit system: ActorSystem): Unit = {
    // Akka HTTP still needs a classic ActorSystem to start
    import system.dispatcher

    val db = Database.forConfig("oneq.database")
    val userRepository: UserRepository = new UserRepository(db)
    val userRoutes = new UserRoutes(userRepository).routes

    val futureBinding = Http(system).newServerAt("localhost", 8080).bind(userRoutes ~ routes)

    userRepository.prepareDB().onComplete {
      case Success(binding) =>
        system.log.info("initialized db")

      case Failure(ex) =>
        system.log.error(s"Failed to connect to database, terminating system:\n| ${ex}")
        system.terminate()
    }

    futureBinding.onComplete {
      case Success(binding) =>
        val address = binding.localAddress
        system.log.info("Server online at http://{}:{}/", address.getHostString, address.getPort)
      case Failure(ex) =>
        system.log.error("Failed to bind HTTP endpoint, terminating system", ex)
        system.terminate()
    }
  }

  def main(args: Array[String]): Unit = {
    implicit val system: ActorSystem = ActorSystem("1q")

    startHttpServer(AllRoutes.routes)
  }
}
