package collections.highfunc

import p2_object.Student

/**
 * min max  保证集合中的元素是可排序的
 * 自定义的类存储在集合中调用  min max   必须可排序
 * T  extends  Ordered[T]   重写排序方法compare
 */

object C4_MinMaxFunc {

  def main(args: Array[String]): Unit = {
    val ls = List[Int](1, 2, 3, 5, 6, 34, 12)
    //集合中的元素可以排序比较
    println(ls.max)
    println(ls.min)

    val stus = List[Student](
      Student(3, "zss", 23),
      Student(2, "lss", 45),
      Student(1, "lny", 34)
    )
    println(stus.min)
  }

}
