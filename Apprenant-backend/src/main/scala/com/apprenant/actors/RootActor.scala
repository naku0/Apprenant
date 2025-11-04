package com.apprenant.actors

import akka.actor.{Actor, ActorRef, Props}
import com.apprenant.messages.*
import com.apprenant.models.Room
import java.util.UUID

class RootActor extends Actor{
  private var rooms: Map[String, ActorRef] = Map()

  def receive = {
    case CreateRoom(roomName, host) => {
      val roomId = UUID.randomUUID().toString.take(8)
      val room = Room(roomId, roomName)
      val roomActor = context.actorOf(Props(new RoomActor(room)), s"room-$roomId")
      
      rooms += (roomId -> roomActor)
      roomActor ! JoinRoom(host, sender())
      
      sender() ! roomId
    }
    case GetRoom(roomId) => {
      rooms.get(roomId) match {
        case Some(roomActor) => sender() ! roomActor
        case None => sender() ! RoomNotFound
      }
    }
  }
}
