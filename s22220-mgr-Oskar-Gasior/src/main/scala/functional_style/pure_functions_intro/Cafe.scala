package functional_style.pure_functions_intro

class Cafe {
  def buyCoffeeImpure (cc: CreditCard): Coffee = {
    val cup = new Coffee()
    cc.charge(cup.price) // Side effect
    cup
  }

  def buyCoffeeTestable(cc: CreditCard, p: Payments): Coffee = {
    val cup = new Coffee()
    p.charge(cc, cup.price) // Side effect
    cup
  }

  def buyCoffeePure(cc: CreditCard): (Coffee, Charge) = {
    val cup = new Coffee()
    (cup, Charge(cc, cup.price))
  }

}
