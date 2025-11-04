package com.apprenant.messages

import akka.actor.ActorRef
import com.apprenant.dto.CursorPositionDto
import com.apprenant.models.{Participant, Room}

//root actor
case class CreateRoom(roomName: String, host: Participant)
case class GetRoom(roomId: String)
case object RoomNotFound

//room actor
case class JoinRoom(participant: Participant, wsActor: ActorRef)
case class LeaveRoom(participantId: String)
case class CodeUpdate(code: String, participantId: String, timestamp: Long)
case class CursorMove(position: CursorPositionDto, participantId: String)
case class ChangeLanguage(language: String, participantId: String)
case object GetRoomState

case class RoomState(
                    room: Room,
                    code: String,
                    language: String,
                    participants: List[Participant],
                    cursorPositions: Map[String, CursorPositionDto]
                    )

//broadcast msgs
sealed trait BroadcastMessage
case class UserJoined(user: Participant, allParticipants: List[Participant]) extends BroadcastMessage
case class UserLeft(userId: String, allParticipants: List[Participant]) extends BroadcastMessage
case class CodeChanged(code: String, byUser: String, timestamp: Long) extends BroadcastMessage
case class CursorUpdated(position: CursorPositionDto, userId: String) extends BroadcastMessage
case class LanguageChanged(language: String, byUser: String) extends BroadcastMessage