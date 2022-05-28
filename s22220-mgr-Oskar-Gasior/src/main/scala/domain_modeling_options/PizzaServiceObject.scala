package domain_modeling_options

object PizzaServiceObject extends PizzaService {
  override def removeIngredient(p: Pizza, ingredient: String): Pizza = ???

  override def removeAllIngredients(p: Pizza): Pizza = ???
}
