package functional_style

import scala.annotation.tailrec

object Recursion {

  def factorialRecursion(n: Int): BigInt = {
    if(n == 1) 1
    else factorialRecursion(n - 1) * n
  }

  def factorialTailRecursion(n: Int): BigInt = {
    def loop(n: Int, acc: BigInt): BigInt =
      if (n <= 0) acc
      else loop(n - 1, n * acc)
    loop(n, BigInt(1))
  }

  def factorialTailRecursionOptimised(n: Int): BigInt = {
    @tailrec
    def loop(n: Int, acc: BigInt): BigInt =
      if (n <= 0) acc
      else loop(n - 1, n * acc)
    loop(n, BigInt(1))
  }
}
