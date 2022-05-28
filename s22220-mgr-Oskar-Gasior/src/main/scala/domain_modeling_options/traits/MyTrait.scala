package domain_modeling_options.traits

trait MyTrait {
  def standardMethod(param: String): Unit = println(param)
  def abstractMethod(): Unit
  val standardField: Int = 0
  val abstractField: Int
}
