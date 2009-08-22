package eero.chat.snippet

import eero.chat.model._
import eero.chat.comet._

import net.liftweb._ 
import http._ 
import SHtml._ 
import S._ 
 
import js._ 
import JsCmds._ 
 
import mapper._ 
 
import util._ 
import Helpers._

import java.util.Date 

import scala.xml.{NodeSeq, Text, Node} 

class ChatRoom
{
//  object room extends SessionVariable
  
  def say(form: NodeSeq) = 
  {
  	
    val message = Message.create.sender(User.currentUser).room(1)
    
    def saveAndSend(msg: Message):Unit =
    {
      msg.date(new Date).save 
      DefaultRoom ! Send (msg)
    }
    
    def checkAndSend(): Unit =
	  message.validate match 
      {
	   	case Nil => saveAndSend(message)
	   	case xs => S.error(xs);
    }
    
    def doBind(form: NodeSeq) =
    	bind("message", form,
             "text" -> message.text.toForm,
             "submit" -> submit("Say", checkAndSend))
    
    doBind(form)
  }

}
