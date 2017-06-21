package org.ababup1192

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer
import play.api.libs.json._
import play.api.libs.ws._
import play.api.libs.ws.ahc.StandaloneAhcWSClient

import scala.concurrent.Future


object Main extends App {
  val jsonText =
    """
       [
         {"name": "John", "age": 25, "role": "Driver"},
         {"name": "Mark", "age": 35, "role": "Teacher"},
         {"name": "Mary", "age": 18, "role": "Student"}
       ]
    """

  val json = Json.parse(jsonText)

  // Simple Access
  println(json \\ "name")

  implicit val humanReads = Json.reads[Human]
  val jsonResult = Json.fromJson[Seq[Human]](json)

  // Using Macro and case class
  for (
    humans <- jsonResult;
    human <- humans
  ) {
    println(human)
  }

  import scala.concurrent.ExecutionContext.Implicits._

  implicit val system = ActorSystem()
  implicit val materializer = ActorMaterializer()

  val wsClient = StandaloneAhcWSClient()

  call(wsClient)
    .andThen { case _ => wsClient.close() }
    .andThen { case _ => system.terminate() }

  def call(wsClient: StandaloneWSClient): Future[Unit] = {
    wsClient.url("https://httpbin.org/get").get().map { response ⇒
      val body = response.body
      val json = Json.parse(body)
      val headers = json \ "headers"
      println(headers \ "Connection")
      println(headers \ "Host")
      println(headers \ "User-Agent")
      println(json \ "origin")
      println(json \ "url")
    }
  }

}

case class Human(name: String, age: Int, role: String)


