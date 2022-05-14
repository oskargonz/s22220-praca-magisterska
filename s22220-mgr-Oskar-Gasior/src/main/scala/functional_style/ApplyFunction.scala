package functional_style

class ApplyFunction {
  // Object that contains fields and method.
  val isLessThan = new ((Int, Int) => Boolean) {
    val thisIsInt = 123
    val thisIsString = "Some String"
    def apply(a: Int, b: Int): Boolean = a < b
  }
  isLessThan.thisIsInt
  isLessThan.thisIsString

  // A method that is similar to above isLessThan but do not have fields.
  def isLessThanDef(a: Int, b: Int): Boolean = {
    a < b
  }

  // isLessThan object with type annotation.
  val isLessThanWithType: ((Int, Int) => Boolean) = new ((Int, Int) => Boolean) {
    val thisIsInt = 123
    val thisIsString = "Some String"
    def apply(a: Int, b: Int): Boolean = a < b
  }
  // scala> isLessThanWithType.thisIsInt
  // Output>  value thisIsInt is not a member of (Int, Int) => Boolean
  //          isLessThanWithType.thisIsInt
}
