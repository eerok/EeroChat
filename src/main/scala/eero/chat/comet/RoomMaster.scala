package eero.chat.comet

import eero.chat.model._
import scala.collection.mutable.HashMap
import scala.collection.mutable.ListBuffer
import scala.actors._
import scala.actors.Actor._
import java.lang.System


object RoomLocator
{
  private var rooms = new HashMap[Int, RoomMaster]
  def add (id:Int, room:RoomMaster) = rooms(id) = room
  
  def find(id:Int):Option[RoomMaster] =
  {
    val room = rooms(id)
    
    if (room == null) None
    else new Some(room)
  }
}

object DefaultRoom extends RoomMaster

class RoomMaster extends Actor
{
  var listeners = new HashMap[Int, Actor]
  private var members = List[User]()
  
  private def broadCast(m:Any) = listeners.values.foreach( _ ! m )
  
  private def updateMembershipInfo(listener:Actor) = members.foreach( listener ! Join (_) )
  
  def act = loop
  {
    react
    {
      case Subscribe(user:Actor) =>
        listeners += user.hashCode -> user
        updateMembershipInfo(user)
	    
	  case UnSubscribe(user:Actor) =>
	    listeners -= user.hashCode
      
	  case j@Join(user:User) =>
	    broadCast(j)
	    members = user :: members
   
	  case l@Leave(user:User) =>
	    broadCast(l)
	    members = members.remove( _  equals user)
	  
	  case msg =>
	    broadCast(msg)
    }
  }

}
