package domain_modeling_options

trait PizzaService extends PizzaServiceInterface {
  def addIngredient(p: Pizza, ingredient: String): Pizza = {
    val newIngredient = ingredient
    p.copy(additionalIngredient = newIngredient)
  }
  def updateCrustSize(p: Pizza, cs: String): Pizza = {
    val newSize = cs
    p.copy(crustSize = newSize)
  }
  def updateCrustType(p: Pizza, ct: String): Pizza = {
    val newType = ct
    p.copy(crustSize = newType)
  }
}
