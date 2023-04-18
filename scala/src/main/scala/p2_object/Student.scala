package p2_object

class Student(val id: Int, val name: String, val age: Int) extends Ordered[Student]{
  override def compare(that: Student): Int = {
    age-that.age
  }

  override def toString: String =s"Student{$id,$name,$age}"
}

object  Student{
  def apply(id: Int, name: String, age: Int): Student = new Student(id, name, age)
}
