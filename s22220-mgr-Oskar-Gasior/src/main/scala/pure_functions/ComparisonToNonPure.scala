package pure_functions

object ComparisonToNonPure {
  /**
   * Prints Array[String]. Method implemented in functional style.
   *
   * @param strArray Array of String.
   */
  def printFunctionalStyle(strArray: Array[String]): Unit = {
    strArray.foreach(println)
  }

  /**
   *  Returns Array[String]. Method implemented in functional style.
   *
   * @param strArray Array of String.
   */
  def formatArgsPureFunction(strArray: Array[String]): String = strArray.mkString(":")
}
