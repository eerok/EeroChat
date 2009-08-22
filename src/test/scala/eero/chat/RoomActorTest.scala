package eero.chat

import junit.framework._
import Assert._
import scala.xml.XML
import net.liftweb.util._

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

class RoomMasterTest extends TestCase
{
 
  var room = new RoomMaster
  var pete = new MockUser
  val hello = Message.create.text("Hello Room 1").room(1)
  val helloRoom2 = Message.create.text("Hello Room 2").room(2)
  val nick = "Mr.T"
  val sender = User.create.nick(nick)

  override def setUp =
  {
    room = new RoomMaster
    pete = new MockUser
    room.start
    pete.start
    room ! Join( sender )
    room ! Subscribe( pete )
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
  
  def testLocateAndSend
  {
    val id = 1
    RoomLocator.add( id, room )
    RoomLocator.add( 2, new RoomMaster )
    RoomLocator.add( 67, new RoomMaster )
    room = RoomLocator.find(id) getOrElse new RoomMaster
    testOneUserJoinAndSend
  }
  
  def testTwoUsers = 
  {
    val laura = new MockUser
    laura.start
    room ! Subscribe( laura )
    testOneUserJoinAndSend
    val received = laura.latest
	assertNotNull("Did not receive anything!", received)
	assertEquals("Sent message id should match received", received.id, hello.id)
  }
  
  def testLeaveRoom =
  {
    room ! Leave( sender )
    room ! UnSubscribe( pete )
    Thread.sleep(100)
    room ! Send( hello )
    Thread.sleep(100)
    val received = pete.latest
	assertNull("Should not receive anything", received)
  }
}
