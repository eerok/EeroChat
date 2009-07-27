package eero.chat.comet

import eero.chat.model._

import scala.actors._
import scala.xml._
import scala.collection.mutable.ListBuffer

import net.liftweb.http._


class UserActor extends CometActor
{
  
  private var messages = List()
  
  def getMessages = messages
  def render = { new Text("Hello")}
  
}
