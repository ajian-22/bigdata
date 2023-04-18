package p4_collections.highfunc

/**
 * 当集合中的数据是排序的  take(n)  topN
 */
object C7_TakeFunc {

  def main(args: Array[String]): Unit = {
    val arr = Array("ca", "cb", "c", "d", "e", "f")
    val res1: List[String] = arr.take(2).toList
    println(res1)
    //默认从左边取数据  可以从右边
    val res2: Array[String] = arr.takeRight(2)
    // res1.foreach(println)
    // 从左到右判断  遇到不符合要求的元素终止
    //val res2: Array[String] = arr.takeWhile(_.startsWith("c"))
    // res2.foreach(println)


  }

}
