package com.apprenant.actors

import akka.actor.{Actor, ActorRef}
import com.apprenant.dto.CursorPositionDto
import com.apprenant.messages.*
import com.apprenant.models.{Participant, Room}

class RoomActor(room: Room) extends Actor{
  private var participants: Map[String, Participant] = Map()
  private var code: String = ""
  private var language: String = "Java"
  private var cursorPositions: Map[String, CursorPositionDto] = Map()
  private var wss: Map[String, ActorRef] = Map()

  def receive = {
    case JoinRoom(participant, wsActor) => {
      participants += (participant.id -> participant)
      wss += (participant.id -> wsActor)

      val state = RoomState(room, code, language, participants.values.toList, cursorPositions)
      wsActor ! state
      broadcast(UserJoined(participant, participants.values.toList))
    }
    
    case CodeUpdate(newCode, participantId, _) => {
      code = newCode 
      broadcast(CodeChanged(newCode, participantId, System.currentTimeMillis()))
    }

    case CursorMove(position, participantId) => {
      cursorPositions += (participantId -> position)
      broadcast(CursorUpdated(position, participantId))
    }

    case GetRoomState =>{
      val state = RoomState(room, code, language, participants.values.toList, cursorPositions)
      sender() ! state
    }

    case LeaveRoom(participantId) => {
      participants -= participantId
      cursorPositions -= participantId
      wss -= participantId
      broadcast(UserLeft(participantId, participants.values.toList))
    }
  }

  private def broadcast(message: BroadcastMessage): Unit = {
    wss.values.foreach(_ ! message)
  }
}
