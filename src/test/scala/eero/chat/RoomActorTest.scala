package eero.chat

import _root_.junit.framework._
import Assert._
import _root_.scala.xml.XML
import _root_.net.liftweb.util._

import java.lang.Thread

import eero.chat.model._
import eero.chat.comet._

import scala.actors._
import scala.actors.Actor._

class MockUser extends Actor
{
  
  var latest:Message = null
  
  def act = react
  {
    case Send(msg) => latest = msg
  }
}

class RoomActorTest extends TestCase
{
 
  var room = new RoomActor
  var pete = new MockUser
  val hello = Message.create.text("Hello Room 1").room(1)
  val helloRoom2 = Message.create.text("Hello Room 2").room(2)

  override def setUp =
  {
    room = new RoomActor
    pete = new MockUser
    room.start
    pete.start
    room ! Join( pete )
    Thread.sleep(100)
  }
  override def tearDown = 
  {
    //room.exit
	//pete.exit
  }
  
  def testOneUserJoinAndSend = 
  {
    room ! Send( hello )
    Thread.sleep(100)
	val received = pete.latest
	assertNotNull("Did not receive anything!", received)
	assertEquals("Sent message id should match received", received.id, hello.id)
  }
  
  def testSendToWrongRoom =
  {
	room ! Send( helloRoom2 )
	Thread.sleep(100)
	val received = pete.latest
	assertNull("Should not receive anything", received)
  }
  
  def testTwoUsers = 
  {
    val laura = new MockUser
    laura.start
    room ! Join( laura )
    testOneUserJoinAndSend
    val received = laura.latest
	assertNotNull("Did not receive anything!", received)
	assertEquals("Sent message id should match received", received.id, hello.id)
  }
  
  def testLeaveRoom =
  {
    room ! Leave( pete )
    Thread.sleep(100)
    room ! Send( hello )
    Thread.sleep(100)
    val received = pete.latest
	assertNull("Should not receive anything", received)
  }
}
