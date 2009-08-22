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

class MessageListTest extends TestCase
{
  val nick = "Eero"
  val msgTxt = "Hello World!"
  val user = User.create.nick(nick)
  val hello = Message.create.sender(user).text(msgTxt)
  val hello2 = Message.create.sender(user).text(msgTxt)
  var actor = new MessageList
  
  val tmpl = <chat:messages></chat:messages>
  
  override def setUp =
  {
	  actor = new MessageList
	  actor.start
  }
  
  /*
  def testSingleMessage = 
  {
    actor ! Send (hello)
    Thread.sleep(100)
    var nodes = actor.render
    print( nodes )
    assertEquals("Only one message expected", 1, actor.messages.length )
  }
  
  def testTwoMessages = 
  {
    actor ! Send (hello)
    actor ! Send (hello2)
    var nodes = actor.render
    print (nodes)
    assertEquals("Two messages expected", 2, actor.messages.length )
  }
  */
}
