package p2_object

/**
 * 类  类似于javaBean封装数据
 */
object Student2 {
  def apply(id: Int, name: String, age: Int): Student2 = new Student2(id, name, age)
}

class Student2(val id: Int, val name: String, val age: Int) {
  override def toString: String = s"Student($id,$name,$age)"
}