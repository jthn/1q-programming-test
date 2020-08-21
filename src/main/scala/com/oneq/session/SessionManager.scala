package com.oneq.session

import com.softwaremill.session.{
  JValueSessionSerializer,
  JwtSessionEncoder,
  SessionConfig,
  SessionManager,
  SessionSerializer
}

case class SessionData(email: String)

object SessionData {
  implicit val serializer = JValueSessionSerializer.caseClass[SessionData]
  implicit val encoder = new JwtSessionEncoder[SessionData]
  implicit val manager = new SessionManager[SessionData](SessionConfig.fromConfig())
}
