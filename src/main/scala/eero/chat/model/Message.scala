package eero.chat.model

import _root_.net.liftweb._
import mapper._
import util._
import scala.xml._

object Message extends Message with LongKeyedMetaMapper[Message]{
  
}

class Message extends LongKeyedMapper[Message] with IdPK {
  
  def getSingleton = Message
  object sender extends MappedLongForeignKey(this, User)
  object room extends MappedLongForeignKey(this, Room)
  object text extends MappedPoliteString(this, 128)
  object date extends MappedDateTime(this)
  
  var colors = "#FF0000" :: "#FF00FF" :: "#99FF66" :: "#6633FF" ::
	  		   "#000066" :: "#993333" :: Nil 
  
  
  def senderNick: NodeSeq = 
   {
    val mrT = User.create.nick("Mr. T")

    def findSender(msg:Message) = msg.sender.obj openOr mrT
    def pickColor(l:Long) = colors drop (l % colors.length ).toInt head
    
    val sender = findSender(this)
    val color = "color:" + pickColor (sender.id.is)
    
    <span style={color}><em>{sender.nick.is}</em></span>
  }
  
  def render():NodeSeq = 
    senderNick ++  Text (": " + this.text) ++ <br/> 
}