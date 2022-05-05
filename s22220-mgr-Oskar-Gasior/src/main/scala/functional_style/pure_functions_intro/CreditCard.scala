package functional_style.pure_functions_intro

class CreditCard {
  var balance = 1000
  def charge(payment: Int): Int = balance - payment
}
