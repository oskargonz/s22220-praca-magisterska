package domain_modeling_options.objects

class UserClass {
  private var ID = 0
  def newID(): Int = { ID += 1; ID }
}
