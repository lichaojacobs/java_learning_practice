package com.jacobs.akka.scala

import akka.actor.{Actor, ActorLogging, ActorRef, ActorSystem, Props}
import com.jacobs.akka.scala.Printer.Greeting

import scala.io.StdIn

object Greeter {
  def props(message: String, printerActor: ActorRef): Props = Props(new Greeter(message, printerActor))

  final case class WhoToGreet(who: String)

  case object Greet

}

class Greeter(message: String, printerActor: ActorRef) extends Actor {

  import Greeter._

  var greeting = ""

  def receive = {
    case WhoToGreet(who) =>
      greeting = s"$message, $who"
    case Greet =>
      printerActor ! Greeting(greeting)
  }
}

object Printer {
  def props: Props = Props[Printer]

  final case class Greeting(greeting: String)

}

class Printer extends Actor with ActorLogging {

  import Printer._

  def receive = {
    case Greeting(greeting) =>
      log.info(s"Greeting received (from ${sender()}): $greeting")
  }
}

object QuickStartMain extends App {

  import Greeter._

  // Create the 'helloAkka' actor system
  val system: ActorSystem = ActorSystem("helloAkka")

  try {
    // Create the printer actor
    val printer: ActorRef = system.actorOf(Printer.props, "printerActor")

    // Create the 'greeter' actors
    val howdyGreeter: ActorRef =
      system.actorOf(Greeter.props("Howdy", printer), "howdyGreeter")
    val helloGreeter: ActorRef =
      system.actorOf(Greeter.props("Hello", printer), "helloGreeter")
    val goodDayGreeter: ActorRef =
      system.actorOf(Greeter.props("Good day", printer), "goodDayGreeter")

    howdyGreeter ! WhoToGreet("Akka")
    howdyGreeter ! Greet

    howdyGreeter ! WhoToGreet("Lightbend")
    howdyGreeter ! Greet

    helloGreeter ! WhoToGreet("Scala")
    helloGreeter ! Greet

    goodDayGreeter ! WhoToGreet("Play")
    goodDayGreeter ! Greet

    println(">>> Press ENTER to exit <<<")
    StdIn.readLine()
  } finally {
    system.terminate()
  }
}
