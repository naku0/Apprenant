package com.apprenant.models

case class Participant(id: String, name: String)

case class Room(roomId: String, roomName: String)

case class Session(sessionId: String, roomId: String, participants: List[Participant])
