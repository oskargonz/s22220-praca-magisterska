package best_practices

object ImmutableVariables {
  // Better implementation of function foo from MutableVariables.scala without var.
  //
  //   Input: foo(1)
  //   Output: res0: Int = 1
  //
  //   Input: foo(1)
  //   Output: res0: Int = 1

  val i: Int = 0
  def foo(j: Int): Int = {
    val x = i + j
    x
  }
}
