package com.apprenant

import akka.actor.{ActorRef, Props}
import akka.actor.typed.ActorSystem
import cats.effect.*
import com.apprenant.actors.SessionManagerActor
import com.apprenant.routes.{SessionRoutes, WebSocketRoutes}
import com.comcast.ip4s.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import org.http4s.implicits.*

import scala.concurrent.ExecutionContext

object Main extends IOApp {

  val httpRoutes = Router(
    "/api" -> new SessionRoutes().routes,
    //"/ws" -> new WebSocketRoutes(sessionManager).routes
  ).orNotFound

  override def run(args: List[String]): IO[ExitCode] = {
    EmberServerBuilder.default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpApp(httpRoutes)
      .build
      .use(server =>
        println("Server started")
        IO.never
      )
      .as(ExitCode.Success)
  }
}
