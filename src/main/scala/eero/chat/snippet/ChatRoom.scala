package eero.chat.snippet

import eero.chat.model._

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

class ChatRoom {
  
  def say(form: NodeSeq) = 
  {
  	
    val message = Message.create.sender(User.currentUser)
    
    def checkAndSave(): Unit =
	  message.validate match 
      {
	   	case Nil => message.date(new Date).save;
	   	case xs => S.error(xs);
       }
    
    def doBind(form: NodeSeq) =
    	bind("message", form,
             "text" -> message.text.toForm,
             "submit" -> submit("Say", checkAndSave))
    
    doBind(form)
  }
  /**
   Todo:implement username thingie
   */
  def messages(html: NodeSeq):NodeSeq =
  {
    def getMsgs = Message.findAll(OrderBy(Message.date, Descending))
    
    def formatMsg(msg:Message):NodeSeq = 
      Text (msg.senderNick + "@" + msg.date + ": " + msg.text) ++ <br/> 
    
    getMsgs.flatMap( formatMsg )
  }
}
