package domain_modeling_options

trait PizzaServiceInterface {
  def addIngredient(p: Pizza, ingredient: String): Pizza
  def removeIngredient(p: Pizza, ingredient: String): Pizza
  def removeAllIngredients(p: Pizza): Pizza
  def updateCrustSize(p: Pizza, cs: String): Pizza
  def updateCrustType(p: Pizza, ct: String): Pizza
}
