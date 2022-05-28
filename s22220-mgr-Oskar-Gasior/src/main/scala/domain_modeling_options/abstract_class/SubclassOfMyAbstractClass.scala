package domain_modeling_options.abstract_class

class SubclassOfMyAbstractClass extends MyAbstractClass {
  override def abstractMethod(): Unit = println()

  override val abstractField: Int = 0

  override val standardField = 100
}
