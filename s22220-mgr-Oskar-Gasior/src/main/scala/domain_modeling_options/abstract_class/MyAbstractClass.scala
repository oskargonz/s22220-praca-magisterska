package domain_modeling_options.abstract_class

abstract class MyAbstractClass {
  def standardMethod(param: String): Unit = println(param)
  def abstractMethod(): Unit
  val standardField: Int = 0
  val abstractField: Int
}
