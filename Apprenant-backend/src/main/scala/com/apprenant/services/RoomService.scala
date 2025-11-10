package com.apprenant.services

import akka.actor.{ActorRef, ActorSystem, Props}
import akka.actor.typed.delivery.internal.ProducerControllerImpl.Request
import com.apprenant.actors.RoomActor
import com.apprenant.dto.{CreateRoomRequest, CreateRoomResponse}
import com.apprenant.messages.JoinRoom
import com.apprenant.models.{Participant, Room}

import java.util.UUID
import scala.concurrent.Future

class RoomService(actorSystem: ActorSystem){
  private var rooms: Map[String, ActorRef] = Map()

  def createRoom(request: CreateRoomRequest): CreateRoomResponse = {
    val roomId = UUID.randomUUID().toString.take(8)
    val host = Participant(UUID.randomUUID().toString.take(8), request.hostName)
    val room = Room(roomId = roomId, roomName = request.roomName)

    val roomActor = actorSystem.actorOf(Props(new RoomActor(room)), s"room-$roomId")
    rooms += (roomId -> roomActor)

    //roomActor ! JoinRoom(host, ???)
    CreateRoomResponse(roomId)
  }

  def getRoom(roomId: String): Future[Option[ActorRef]] = {
    Future.successful(rooms.get(roomId))
  }

  def roomExists(roomId: String): Future[Boolean] = {
    Future.successful(rooms.contains(roomId))
  }
}
