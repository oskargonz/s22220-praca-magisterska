package functional_style

import scala.annotation.tailrec

object PolymorphicFunctions {
  def findFirstElementString(dataSource: Array[String], elementToFind: String): Int = {
    @tailrec
    def go(num: Int): Int =
      if (num >= dataSource.length) -1
      else if (dataSource(num) == elementToFind) num
      else go(num + 1)
    go(0)
  }

  def findFirstElementInt(dataSource: Array[Int], elementToFind: Int): Int = {
    @tailrec
    def go(num: Int): Int =
      if (num >= dataSource.length) -1
      else if (dataSource(num) == elementToFind) num
      else go(num + 1)
    go(0)
  }

  def findFirstElement[A](dataSource: Array[A], find: A => Boolean): Int = {
    @tailrec
    def go(num: Int): Int =
      if (num >= dataSource.length) -1
      else if (find(dataSource(num))) num
      else go(num + 1)
    go(0)
  }
}
