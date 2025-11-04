package com.apprenant

import akka.actor.{Props, ActorSystem}
import cats.effect.*
import com.apprenant.actors.RootActor
import com.apprenant.routes.SessionRoutes
import com.comcast.ip4s.*
import org.http4s.ember.server.EmberServerBuilder
import org.http4s.server.Router
import org.http4s.implicits.*

object Main extends IOApp {
  override def run(args: List[String]): IO[ExitCode] = {
    implicit val system: ActorSystem = ActorSystem("apprenant")
    val rootActor = system.actorOf(Props[RootActor](), "root")

    val sessionRoutes = new SessionRoutes(rootActor).routes
    //val wsRoutes = new WebSocketRoutes(rootActor).routes

    EmberServerBuilder.default[IO]
      .withHost(ipv4"0.0.0.0")
      .withPort(port"8080")
      .withHttpWebSocketApp(wsb =>
        Router(
          "/api" -> sessionRoutes,
          //"/ws" -> wsRoutes.routes(wsb)
        ).orNotFound)
      .build
      .use(server =>
        println("Server started")
        IO.never
      )
      .as(ExitCode.Success)
  }
}
