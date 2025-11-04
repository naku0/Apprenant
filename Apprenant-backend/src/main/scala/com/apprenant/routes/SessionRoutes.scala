package com.apprenant.routes

import akka.actor.ActorRef
import akka.pattern.ask
import akka.util.Timeout
import cats.effect.IO
import com.apprenant.dto.{CreateRoomRequest, CreateRoomResponse, ParticipantDto, RoomDto}
import com.apprenant.messages.{CreateRoom, GetRoom, GetRoomState, RoomNotFound, RoomState}
import com.apprenant.models.Participant
import io.circe.Encoder.AsArray.importedAsArrayEncoder
import io.circe.Encoder.encodeString
import io.circe.Json
import org.http4s.HttpRoutes
import org.http4s.Method.GET
import org.http4s.dsl.io.*
import io.circe.generic.auto.*
import io.circe.syntax.*
import org.http4s.circe.CirceEntityCodec.*

import java.util.UUID
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration.DurationInt

class SessionRoutes(rootActor: ActorRef){

  implicit val timeout: Timeout = Timeout(3.seconds)

  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case req @ POST -> Root / "rooms" =>
      for {
        createReq <- req.as[CreateRoomRequest]
        participant = Participant(UUID.randomUUID().toString, createReq.hostName)
        roomId <- toIO(rootActor ? CreateRoom(createReq.roomName, participant))
        response = CreateRoomResponse(roomId.toString, s"/room/$roomId")
        resp <- Ok(response)
      } yield resp

    case GET -> Root / "rooms" / roomId =>
      toIO(rootActor ? GetRoom(roomId)).flatMap {
        case roomActor: ActorRef =>
          toIO(roomActor ? GetRoomState).flatMap {
            case RoomState(room, code, language, participants, cursors) =>
              val roomDto = RoomDto(room.roomId, room.roomName,
                participants.map(p => ParticipantDto(p.id, p.name)))
              Ok(Json.obj(
                "room" -> roomDto.asJson,
                "code" -> code.asJson,
                "language" -> language.asJson,
                "participants" -> participants.map(_.name).asJson
              ))
          }

        case RoomNotFound =>
          NotFound("Комната не найдена")
      }

    case GET -> Root / "test" =>
      Ok(Json.obj(
        "status" -> "OK".asJson,
        "message" -> "Сервер работает!".asJson
      ))
  }

    private def toIO[A](future: => scala.concurrent.Future[A]): IO[A] = IO.fromFuture(IO(future))
}
