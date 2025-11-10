package com.apprenant.actors

import akka.actor.{Actor, ActorRef, Props}
import com.apprenant.actors.SessionActor
import com.apprenant.messages.{ JoinRoom, CodeUpdate, CursorMove, LeaveRoom, BroadcastToRoom,
  GetRoomState, GetSessionState, JoinSession, LeaveSession, UpdateCode, UpdateCursor}
import com.apprenant.models.Room

class RoomActor(room: Room) extends Actor {
  private val sessionActor = context.actorOf(Props(new SessionActor(room)), "session")
  private var websocketConnections: Map[String, ActorRef] = Map()

  def receive = {
    case JoinRoom(participant, wsActor) =>
      websocketConnections += (participant.id -> wsActor)
      sessionActor ! JoinSession(participant)

    case CodeUpdate(newCode, participantId, timestamp) =>
      sessionActor ! UpdateCode(newCode, participantId)

    case CursorMove(position, participantId) =>
      sessionActor ! UpdateCursor(position, participantId)

    case LeaveRoom(participantId) =>
      websocketConnections -= participantId
      sessionActor ! LeaveSession(participantId)

    case BroadcastToRoom(_, message) =>
      websocketConnections.values.foreach(_ ! message)

    case GetRoomState =>
      sessionActor.forward(GetSessionState)
  }
}