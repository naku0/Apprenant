package com.apprenant.actors

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors
import com.apprenant.models.Participant

object SessionManagerActor{

  sealed trait Command
  case class AddParticipant(participant: Participant, replyTo: ActorRef[Response]) extends Command
  case class RemoveParticipant(id: String, replyTo: ActorRef[Response]) extends Command
  case class GetParticipants(replyTo: ActorRef[Response]) extends Command

  sealed trait Response
  case class ParticipantAdded(participant: Participant) extends Response
  case class ParticipantRemoved(id: String) extends Response
  case class CurrentParticipants(participants: Set[Participant]) extends Response
  case class Error(message: String) extends Response
}
