package domain_modeling_options.companion_object

class Kid (name: String, age: Int) {
  def greeting() : String = s"Hello, my name is $name, and I'm $age years old."
  val fieldFromCompanionObject: Int = Kid.someField
}

object Kid {
  def apply(name: String, age: Int): String = s"Hi $name, it's apply from companion object."
  def printFromCompanionObject(): Unit = print("A simple method from companion object.")
  val someField: Int = 1
}
