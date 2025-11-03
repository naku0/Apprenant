package com.apprenant.routes

import cats.effect.IO
import com.apprenant.models.Participant
import io.circe.Json
import org.http4s.HttpRoutes
import org.http4s.Method.GET
import org.http4s.dsl.io.*
import io.circe.generic.auto._
import io.circe.syntax._
import org.http4s.circe.CirceEntityCodec._

class SessionRoutes{
  private var participans:List[Participant] = List(Participant("1", "dummy"))
    val routes: HttpRoutes[IO] = HttpRoutes.of[IO]{
      case GET -> Root / "sessions" / "test" => {
        Ok(Json.obj(
          "participants" -> participans.asJson
        ))
      }
      case req @ POST -> Root / "sessions" / "participants" => {
        for {
          name <- req.as[String]
          newId = (participans.size + 1).toString
          _ = participans = participans :+ Participant(newId, name)
          response <- Ok(s"Участник $name добавлен")
        } yield response
      }
      case _ => NotFound("lol")
    }
}
