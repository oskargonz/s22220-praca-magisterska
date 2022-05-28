package maps

object MapTupleExample {

  // Construction of simple Map
  val familyAge: Map[String, Int] = Map("Adam" -> 10, "Julka" -> 7, "Justyna" -> 34, "Andrzej" -> 38)

  // Get value by key from Map.
  val ageOfAdam: Int = familyAge("Adam")

  // No requested value in Map keys.
  try {
    familyAge("12")
  } catch {
    case nsee: NoSuchElementException => println("Key not found in this Map.")
  }

  // Get value by key if I'm not sure whether the Map has such key.
  val ageOfMum: Int = familyAge.getOrElse("Mum", default = 0)

  // Tuple implementation
  val myTuple: (Int, String, Double) = (1, "One", 1.1)

  // Access to first element
  val firstElement: Int = myTuple._1

  // Other implementation of Tuple
  val (first, second, third) = (1, "One", 1.1)

  // Access to first element from second implementation
  val firstElementSecondImplementation: Int = first

}
