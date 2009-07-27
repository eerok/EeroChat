package eero.chat

import _root_.junit.framework._
import Assert._
import _root_.scala.xml._
import _root_.net.liftweb.util._

import java.lang.Thread

import eero.chat.model._
import eero.chat.comet._

import scala.actors._
import scala.actors.Actor._

class CometActorTest extends TestCase
{
  var nick = "Eero"
  var msgTxt = "Hello World!"
  var user = User.create.nick(nick)
  var hello = Message.create.sender(user).text(msgTxt)
  var actor = new UserActor
  
  override def setUp =
  {
	  actor = new UserActor
	  actor.start
  }
  
  
  def testSingleMessage = 
  {
      actor ! Send (hello)
      Thread.sleep(100)
      assertFalse("Expected nick not found", actor.getMessages.isEmpty )
  }
  
}
