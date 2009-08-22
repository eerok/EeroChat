package eero.chat.comet

import scala.actors._
import eero.chat.model._


case class Join(user:User)
case class Leave(user:User)

case class Subscribe(actor: Actor)
case class UnSubscribe(actor: Actor)

case class Send(message:Message)