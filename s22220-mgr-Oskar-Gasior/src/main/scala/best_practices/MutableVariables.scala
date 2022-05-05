package best_practices

object MutableVariables {
  // Below example shows problems with mutable variables.
  //
  //   Input: foo(1)
  //   Output: res0: Int = 1
  //
  //   Input: foo(1)
  //   Output: res0: Int = 2

  var i: Int = 0
  def foo(j: Int): Int = {
    i += j
    i
  }

  // Exception to the rule of not using mutable variables.
  def sum(list: List[Int]): Int = {
    var s = 0
    for(i <- list) s += i
    s
  }

  // More clean implementation of above function.
  def sum2(list: List[Int]): Int = list.sum
}
