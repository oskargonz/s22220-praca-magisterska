package domain_modeling_options.classes

class UserDefault(name: String, surname: String){
  // Some statement that is part of constructor.
  println("Start of constructor.")

  // class fields that are part of constructor
  var ID = 123456
  private var age = 0

  // a method call that is part of constructor
  printEmployeeInfo()

  // some methods defined in the class that are not part of constructor
  override def toString = s"$name $surname is identified by number $ID"
  def printEmployeeInfo(): Unit = println(this.toString)

  // Some statement that is part of constructor.
  println("Constructor end.")
}
