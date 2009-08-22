package eero.chat.comet

import eero.chat.model._

import scala.actors._
import scala.actors.Actor._
import scala.xml._
import scala.collection.mutable.ListBuffer

import net.liftweb.http._
import net.liftweb.util._
import net.liftweb.http.SHtml._
import net.liftweb.http.js.JsCmds.SetHtml  

class MessageList extends CometActor
{
  
  override def defaultPrefix = Full("chat")
  var messages = List[Message]()
  private val user = User.currentUser.getOrElse( null )
  
  def msgList = messages.flatMap( _.render )
  
  def render = bind( "messages"-> msgDiv)
  
  
  def msgDiv = 
    <div id="messages" style="height: 300px; overflow : auto;">
    {msgList}
    </div>

    
  override def lowPriority =
  {
    case Send(message: Message) =>
      {
        messages = message :: messages
        partialUpdate(SetHtml("messages", msgList))
      }
  }

  override def localSetup 
  {
    assert( user != null , "Not logged in")
    DefaultRoom ! Subscribe(this)
    DefaultRoom ! Join(user)
    super.localSetup
  }
  
  override def localShutdown 
  {
    DefaultRoom ! UnSubscribe(this)
    DefaultRoom ! Leave(user)
    super.localShutdown
  }
}
