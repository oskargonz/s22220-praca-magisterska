package Other

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
}
