package eero.chat.comet

import scala.actors._
import scala.xml._
import net.liftweb.http._

class UserActor extends CometActor
{
	def render = { new Text("Hello")}
}
