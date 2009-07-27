package eero.chat.model

import _root_.net.liftweb._
import mapper._
import util._
import scala.xml.Text

object Message extends Message with LongKeyedMetaMapper[Message]{
  
}

class Message extends LongKeyedMapper[Message] with IdPK {
  
  def getSingleton = Message
  object sender extends MappedLongForeignKey(this, User)
  object room extends MappedLongForeignKey(this, Room)
  object text extends MappedPoliteString(this, 128)
  object date extends MappedDateTime(this)
  
  def senderNick: Text = 
   {
    def findSender(id:Long) = User.findAll( By(User.id, id) ).head
    
    Text ( findSender(this.sender.is).nick.is )
  }
}