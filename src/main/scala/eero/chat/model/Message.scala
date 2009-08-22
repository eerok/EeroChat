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
  
  /**
   * Default administrator user for notifications
   */
  
  val mrT = User.create.nick("Mr. T")

  
  def render():NodeSeq = 
    findSender.render ++  Text (": " + this.text) ++ <br/> 
  
  private def findSender() = this.sender.obj openOr mrT

}