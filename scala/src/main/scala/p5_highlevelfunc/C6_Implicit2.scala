package p5_highlevelfunc

import p2_object.Student


object C6_Implicit2 {

  implicit val x: Ordering[Student] = new Ordering[Student] {
    override def compare(x: Student, y: Student): Int = {
      y.id - x.id
    }
  }

  /*implicit def ordStu: Ordering[MyStu] = {

  }*/


  def main(args: Array[String]): Unit = {

    // val ls: List[Int] = List[Int](1, 23, 21, 4, 7, 2)
    val ls2: List[Student] = List[Student](Student(1,"zss", 21), Student(4,"lss", 33), Student(6,"ny", 1))
    //   def sorted[B >: A](implicit ord: Ordering[B]): Repr =  没有传递参数
    // 说明上下文中有一个对应的隐式变量
    //al list: List[Int] = ls2.sorted
    // 上下文中找 隐式参数  Ordering  比较器
    val ls: List[Student] = ls2.sorted
    println(ls)


  }

}
