package com.oneq

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import spray.json._

import com.oneq.session._
import com.oneq.user._

trait JsonSupport extends SprayJsonSupport with DefaultJsonProtocol {
  implicit val userFormat = jsonFormat3(User)
  implicit val loginFormat = jsonFormat2(UserLogin)
}
