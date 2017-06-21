package org.ababup1192

import play.api.libs.json._

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
}

case class Human(name: String, age: Int, role: String)
