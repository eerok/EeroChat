package eero.chat.comet

import eero.chat.model._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.actors._
import scala.actors.Actor._
import java.lang.System

case class Join(user:Actor)
case class Leave(user:Actor)
case class Send(message:Message)


object RoomLocator
{
  private var rooms = new HashMap[Int, RoomActor]
  def add (id:Int, room:RoomActor) = rooms(id) = room
  def find(id:Int):Option[RoomActor] =
  {
    val room = rooms(id)
    if (room == null) None
    else new Some(room)
  }
}

object DefaultRoom extends RoomActor

class RoomActor extends Actor
{
  var members = new HashMap[Int, Actor]

  def notifyMembers(message:Message)
  {
    members.values.foreach( _ ! Send(message) )
  }
  
  def act = loop
  {
    react
    {
    case Send(message: Message) =>
      notifyMembers(message)
    
    case Join(user:Actor) =>
      members += user.hashCode -> user
    
    case Leave(user:Actor) =>
      members -= user.hashCode
    }
  }

}
