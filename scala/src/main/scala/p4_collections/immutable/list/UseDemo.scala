package collections.immutable.list

/**
 * Seq
 */
object UseDemo {
  def main(args: Array[String]): Unit = {
    // 不可变定义
    val ls  =  List[Int](1,2,4,5,6,7)
    val ls2 = 11::12::13::14::15::Nil
    println(ls2)
    ls.length
    ls.size
    ls :+ 12
    13 +: ls
    val ls3 = ls.::(111) //List(111, 1, 2, 4, 5, 6, 7)
    val ls4 = ls.drop(2) // 从左边删除n个元素
    val ls5 = ls.take(3)  // 从左边取出n个元素  返回新的集合
  }

}
