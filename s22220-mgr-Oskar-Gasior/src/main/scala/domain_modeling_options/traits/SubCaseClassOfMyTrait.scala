package domain_modeling_options.traits

case class SubCaseClassOfMyTrait(abstractField: Int) extends MyTrait {
  override def abstractMethod(): Unit = println()

  //override val abstractField: Int = 0

  override val standardField = 100
}
