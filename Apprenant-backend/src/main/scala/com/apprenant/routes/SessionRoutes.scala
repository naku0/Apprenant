package com.apprenant.routes

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import cats.effect.IO
import com.apprenant.dto.{CreateRoomRequest, CreateRoomResponse, ParticipantDto, RoomDto}
import com.apprenant.messages.{CreateRoom, GetRoom, GetRoomState, RoomNotFound, RoomState}
import com.apprenant.models.Participant
import com.apprenant.services.RoomService
import io.circe.Encoder.encodeString
import io.circe.Json
import org.http4s.{HttpRoutes, Method, Uri}
import org.http4s.Method.GET
import org.http4s.dsl.io.*
import io.circe.generic.auto.*
import io.circe.syntax.*
import org.http4s.circe.CirceEntityCodec.*
import org.http4s.server.middleware.CORS
import org.typelevel.ci.CIString

import java.util.UUID
import scala.collection.mutable
import scala.concurrent.duration.DurationInt

case class RoomData(
                     roomId: String,
                     roomName: String,
                     participants: List[ParticipantDto],
                     code: String = "",
                     language: String = "javascript"
                   )
class SessionRoutes(roomService: RoomService) {

  implicit val timeout: Timeout = Timeout(3.seconds)

  val internalRoutes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case req @ POST -> Root / "room" =>
      println("Got POST /room request")
      for {
        createReq <- req.as[CreateRoomRequest]
        _ = println(s"Parsed request: $createReq")
        response = roomService.createRoom(createReq)
        _ = println(s"Created room: ${response.roomId}")
        resp <- Ok(response)
      } yield resp

    case GET -> Root / "room" / roomId =>
      println(s"GET /room/$roomId")
      (for {
        roomOpt <- toIO(roomService.getRoom(roomId))
        resp <- roomOpt match {
          case Some(roomActor) =>
            println(s"Room actor found for $roomId")
            toIO(roomActor ? GetRoomState).flatMap {
              case RoomState(room, code, language, participants, cursorPositions) =>
                println(s"Room state received: ${participants.size} participants")
                val roomDto = RoomDto(room.roomId, room.roomName,
                  participants.map(p => ParticipantDto(p.id, p.name)))
                Ok(Json.obj(
                  "room" -> roomDto.asJson,
                  "code" -> code.asJson,
                  "language" -> language.asJson,
                  "participants" -> participants.map(_.name).asJson
                ))
              case other =>
                println(s"Unexpected response: $other")
                InternalServerError("Unexpected response from room actor")
            }
          case None =>
            println(s"Room $roomId not found")
            NotFound(s"Room $roomId not found")
        }
      } yield resp).handleErrorWith { error =>
        IO.println(s"Error in GET /room/$roomId: ${error.getMessage}") >>
          InternalServerError(s"Server error: ${error.getMessage}")
      }

    case GET -> Root / "test" =>
      println("GET /test")
      Ok(Json.obj(
        "status" -> "OK".asJson,
        "message" -> "Сервер работает!".asJson
      ))
  }

  val routes: HttpRoutes[IO] = CORS.policy
    .withAllowOriginHost(Set(
      org.http4s.headers.Origin.Host(Uri.Scheme.http, Uri.RegName("localhost"), Some(5173)),
      org.http4s.headers.Origin.Host(Uri.Scheme.http, Uri.RegName("127.0.0.1"), Some(5173))
    ))
    .withAllowMethodsIn(Set(Method.GET, Method.POST, Method.OPTIONS, Method.PUT, Method.DELETE))
    .withAllowHeadersIn(Set(
      CIString("Content-Type"),
      CIString("Authorization"),
      CIString("X-Requested-With")
    ))
    .withAllowCredentials(true)
    .apply(internalRoutes)

  private def toIO[A](future: => scala.concurrent.Future[A]): IO[A] =
    IO.fromFuture(IO(future))
}