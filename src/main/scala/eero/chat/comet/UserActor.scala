package eero.chat.comet

import eero.chat.model._

import scala.actors._
import scala.xml._
import scala.collection.mutable.ListBuffer

import net.liftweb.http._


class UserActor extends CometActor
{
  
  var messages = new ListBuffer[Message]

  def render = { new Text("Hello")}
  def renderMsgs = { new Text("Hulloe")}
}
