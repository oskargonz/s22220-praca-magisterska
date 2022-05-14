package functional_style

object PopularTransformations {
  val myCollection: Array[Int] = Array(1,2,3,4,5)
  val myCollectionOfString: Array[String] = Array("John", "James", "Will")
  val mySet = Set("John", 24, "James", 13, "Will", 54)

  // .map example.
  // Use of simple method as a parameter.
  def square(a: Int): Int = a * a
  val myCollectionSquare1: Array[Int] = myCollection.map(square)
  // Use of anonymous function as a parameter.
  val myCollectionSquare2: Array[Int] = myCollection.map(f => f * f)
  // Use of anonymous function as a parameter with a pretty format.
  val myCollectionSquare3: Array[Int] = myCollection.map { (f: Int) => f * f }


  // .filter example.
  val myFilteredCollection1: Array[Int] = myCollection.filter(_ < 3)
  // The same result as above with pretty style.
  val myFilteredCollection2: Array[Int] = myCollection.filter { (f: Int) => f < 3 }

  // .filterNot example.
  val myFilteredNotCollection1: Array[Int] = myCollection.filterNot(_ < 3)
  // The same result as above with pretty style.
  val myFilteredNotCollection2: Array[Int] = myCollection.filterNot { (f: Int) => f < 3 }

  // .foreach example.
  val myForEachCollection: Unit = myCollection.foreach(println)

  // .flatMap example.
  val myFlatMap: Array[Char] = myCollectionOfString.flatMap { (f: String) => f.toLowerCase }
  // flatMap may be performed in two separated steps.
  val myFlatMapStep1: Array[String] = myCollectionOfString.map { (f: String) => f.toLowerCase }
  val myFlatMapStep2: Array[Char] = myFlatMapStep1.flatten

  // .collect example.
  val age: Set[Int] = mySet.collect { case age: Int => age }
}
