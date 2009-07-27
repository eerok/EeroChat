package eero.chat.model

import net.liftweb.mapper._
import net.liftweb.util._


object Room extends Room with LongKeyedMetaMapper[Room]

class Room extends LongKeyedMapper[Room] with IdPK {
  
  def getSingleton = Room
  
  object name extends MappedPoliteString(this, 64)
  object description extends MappedPoliteString(this, 256)

}
