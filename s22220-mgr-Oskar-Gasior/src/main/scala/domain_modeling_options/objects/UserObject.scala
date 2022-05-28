package domain_modeling_options.objects

object UserObject {
  private var ID = 0
  def newID(): Int = { ID += 1; ID }
}
