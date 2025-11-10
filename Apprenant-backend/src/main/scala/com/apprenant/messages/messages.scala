package com.apprenant.messages

import akka.actor.ActorRef
import com.apprenant.dto.CursorPositionDto
import com.apprenant.models.{Participant, Room}

// Root Actor messages
case class CreateRoom(roomName: String, host: Participant)
case class GetRoom(roomId: String)
case object RoomNotFound

// Room Actor messages
case class JoinRoom(participant: Participant, wsActor: ActorRef)
case class LeaveRoom(participantId: String)
case class CodeUpdate(code: String, participantId: String, timestamp: Long)
case class CursorMove(position: CursorPositionDto, participantId: String)
case class ChangeLanguage(language: String, participantId: String)
case object GetRoomState

// Session Actor messages
case class JoinSession(participant: Participant)
case class UpdateCode(newCode: String, participantId: String)
case class UpdateCursor(position: CursorPositionDto, participantId: String)
case class UpdateLanguage(language: String, participantId: String)
case class LeaveSession(participantId: String)
case object GetSessionState

// Session Actor responses
case class JoinSessionSuccess(state: SessionState)
case class CodeUpdated(code: String, participantId: String)
case class CursorUpdated(position: CursorPositionDto, participantId: String)
case class LanguageUpdated(language: String, participantId: String)
case class UserLeftSession(participantId: String, remainingParticipants: List[Participant])

// Состояния
case class SessionState(
                         room: Room,
                         code: String = "",
                         language: String = "Java",
                         participants: Map[String, Participant] = Map(), // исправлено на Map
                         cursorPositions: Map[String, CursorPositionDto] = Map()
                       )

case class RoomState(
                      room: Room,
                      code: String,
                      language: String,
                      participants: List[Participant],
                      cursorPositions: Map[String, CursorPositionDto]
                    )

// Broadcast messages
sealed trait BroadcastMessage
case class UserJoined(user: Participant, allParticipants: List[Participant]) extends BroadcastMessage
case class UserLeft(userId: String, allParticipants: List[Participant]) extends BroadcastMessage
case class CodeChanged(code: String, byUser: String, timestamp: Long) extends BroadcastMessage
case class CursorUpdatedBroadcast(position: CursorPositionDto, userId: String) extends BroadcastMessage
case class LanguageChanged(language: String, byUser: String) extends BroadcastMessage

// Internal broadcast routing
case class BroadcastToRoom(roomId: String, message: BroadcastMessage)