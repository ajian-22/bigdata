package collections.highfunc

/**
 * grouped  指定n个元素为一组
 */
object C3_GroupedFunc {

  def main(args: Array[String]): Unit = {
    val list1 = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11)
    // 每三个数为一组  求和
    val iterator: Iterator[List[Int]] = list1.grouped(3) // 每组几个元素
    //iterator.map(ls => ls.sum).foreach(println)
    iterator.map(_.head).foreach(println)
  }
}
