package functional_style.pure_functions_intro

trait Payments {
  def charge(cc: CreditCard, price: Int)
}
