package com.apprenant.routes

import akka.actor.ActorRef
import cats.effect.IO
import org.http4s.HttpRoutes
import org.http4s.dsl.io.*
import org.http4s.server.websocket.WebSocketBuilder2
import org.http4s.websocket.WebSocketFrame

class WebSocketRoutes(rootActor: ActorRef) {
???
}