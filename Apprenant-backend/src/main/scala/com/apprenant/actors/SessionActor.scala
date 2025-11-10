package com.apprenant.actors

import akka.actor.Actor
import akka.actor.typed.delivery.DurableProducerQueue.TimestampMillis
import com.apprenant.dto.CursorPositionDto
import com.apprenant.messages.{CodeUpdated, GetSessionState, JoinSession, JoinSessionSuccess, SessionState, UpdateCode}
import com.apprenant.models.{Participant, Room}

class SessionActor(initialRoom: Room) extends Actor {
  private var state = SessionState(initialRoom)

  def receive = {
    case JoinSession(participant) =>
      state = state.copy(
        participants = state.participants + (participant.id -> participant)
      )
      sender() ! JoinSessionSuccess(state)

    case UpdateCode(newCode, participantId) =>
      state = state.copy(code = newCode)
      sender() ! CodeUpdated(newCode, participantId)

    case GetSessionState =>
      sender() ! state
  }
}