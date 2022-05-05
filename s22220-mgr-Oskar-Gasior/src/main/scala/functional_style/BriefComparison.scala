package functional_style

object BriefComparison {
  val myArray = Array("Elon Musk", "Steve Jobs")

  /**
   * Prints Array[String]. Method implemented in imperative style.
   *
   * @param strArray Array of String.
   */
  def printImperativeStyle(strArray: Array[String]): Unit = {
    var i = 0
    while (i < strArray.length) {
      println(strArray (i))
      i += 1
    }
  }
  /**
   * Prints Array[String]. Method implemented in functional style.
   *
   * @param strArray Array of String.
   */
  def printFunctionalStyle(strArray: Array[String]): Unit = {
    strArray.foreach(println)
  }
}
