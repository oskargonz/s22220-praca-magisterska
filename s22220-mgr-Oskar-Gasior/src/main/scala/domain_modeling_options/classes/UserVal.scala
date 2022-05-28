package domain_modeling_options.classes

class UserVal (val name: String, val surname: String) {
  // class fields that are part of constructor
  var ID = 123456
  private var age = 0

  // some methods defined in the class that are not part of constructor
  override def toString = s"$name $surname is identified by number $ID"
  def printEmployeeInfo(): Unit = println(this.toString)
}
