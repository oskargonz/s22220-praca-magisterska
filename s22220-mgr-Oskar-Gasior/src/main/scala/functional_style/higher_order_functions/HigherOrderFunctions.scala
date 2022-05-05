package functional_style.higher_order_functions

import functional_style.Recursion

object HigherOrderFunctions {
  def absolute(n: Int): BigInt =
    if (n < 0) BigInt(-n)
    else BigInt(n)

  def formatAbs(x: Int): String = {
    val msg = "The absolute value of %d is %d"
    msg.format(x, absolute(x))
  }

  def formatFactorial(n: Int): String = {
    val msg = "The factorial of %d is %d."
    msg.format(n, Recursion.factorialTailRecursion(n))
  }

  def formatResult(name: String, n: Int, f: Int => BigInt): String = {
    val msg = "The %s of %d is %d."
    msg.format(name, n, f(n))
  }

}
