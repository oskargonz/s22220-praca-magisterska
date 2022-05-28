package pattern_matching

import java.io.{FileNotFoundException, IOException}
import scala.io.Source
import scala.util.control.Exception.allCatch

object MyPatternMatching {
  // Pattern matching as a replacement of switch.
  val dayNumber: Int = 0
  val dayName: String = dayNumber match {
    case 0 => "Monday"
    case 1 => "Tuesday"
    case 2 => "Wednesday"
    case 3 => "Thursday"
    case 4 => "Friday"
    case 5 => "Saturday"
    case 6 => "Sunday"
  }

  // Pattern matching as body of method.
  def isTrue[A](param: A): Boolean = param match {
    case false | 0 | "" => false
    case _ => true
  }

  // Pattern matching with Options.
  def makeInt(s: String): Option[Int] = allCatch.opt(s.trim.toInt)

  val i = 1
  makeInt("1") match {
    case Some(i) => println(i)
    case None => println("Error: Could not convert String to Int.")
  }

  // Pattern matching with Try/Catch.
  def readTextFile(filename: String): Option[List[String]] =
    try {
      Some(Source.fromFile(filename).getLines.toList)
    } catch {
      case ioe: IOException => None
      case fnf: FileNotFoundException => None
    }
}
