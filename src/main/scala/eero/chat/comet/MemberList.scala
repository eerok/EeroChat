package eero.chat.comet

import eero.chat.model._

import scala.actors._
import scala.actors.Actor._
import scala.xml._
import scala.collection.mutable.ListBuffer

import net.liftweb.http._
import net.liftweb.util._
import net.liftweb.http.SHtml._
import  net.liftweb.http.js.JsCmds.SetHtml  

class MemberList extends CometActor
{
  
  override def defaultPrefix = Full("chat")
  private var members = List[User]()
  
  def memberList = 
    {
      <em>Online: </em> ++ members.flatMap( _.render ++ Text(" ")  )
    }
  
  
  def render = bind( "members"-> membersDiv)
  
  def membersDiv =
    <div id="members">
    {memberList}
    </div>
    
  private def update = partialUpdate(SetHtml("members",memberList))

  override def lowPriority =
  {
    case Join(user: User) =>
      members = user :: members
      members = members.removeDuplicates
      update
      
    case Leave(user: User) =>
      members = members.remove( _ equals user )
      update
  }
  
  override def localSetup 
  {
    DefaultRoom ! Subscribe(this)
    super.localSetup
  }
  
  override def localShutdown 
  {
    DefaultRoom ! UnSubscribe(this)
    super.localShutdown
  }
}