package com.apprenant.dto

case class CreateRoomRequest(roomName: String, hostName: String)
case class CreateRoomResponse(roomId: String, joinUrl: String)

case class JoinRoomRequest(userName: String)
case class JoinRoomResponse(success: Boolean, room: RoomDto)

case class RoomDto(roomId: String, roomName: String, participants: List[ParticipantDto])
case class ParticipantDto(id: String, name: String)

sealed trait WebSocketMessage

case class CliMessage(
                     messageType: String,
                     payload: Option[String] = None,
                     fromUser: Option[String] = None,
                     timestamp: Long = System.currentTimeMillis()
                     ) extends WebSocketMessage

case class CodeUpdateDto(code: String, version: Int) extends WebSocketMessage
case class CursorPositionDto(line: Int, column: Int) extends WebSocketMessage
case class RoomStateDto(
                         code: String,
                         language: String,
                         participants: List[String],
                         cursors: Map[String, CursorPositionDto]
                       ) extends WebSocketMessage