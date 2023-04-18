package collections.highfunc

/**
 * partition(f:T=>Boolean)  分2组 符合条件的一组  不符合的一组
 * span(f:T=>Boolean)       分2组   遇到不符合条件的元素 分组终止
 * grouped(n)              指定每组的元素的个数
 * groupby(f:T=>B)
 *
 */
object C3_GroupByFunc {

  def main(args: Array[String]): Unit = {
    val list1 = List(1, 1, 1, 2, 3, 3, 4, 4)
    val list2 = List("scala", "is", "option", "fucntion")
    val mp = Map[String, Int]("a" -> 1, "b" -> 1, "c" -> 2)
    // 1 按照元素本身的值分组
    val res1: Map[Int, List[Int]] = list1.groupBy(e => e)
    // 2 指定某个元素
    val res2: Map[Int, Map[String, Int]] = mp.groupBy(_._2)
    // 3 条件分组  2
    val res3: Map[Boolean, List[String]] = list2.groupBy(_.contains("s"))
    val res4: Map[Boolean, List[Int]] = list1.groupBy(_ > 2)
    val ls = List(("a", 1), ("a", 1), ("a", 1), ("a", 1), ("b", 1), ("b", 1),("c",9))
    val res5 = ls.groupBy(_._1)
    res5.foreach(println)
  }
}
