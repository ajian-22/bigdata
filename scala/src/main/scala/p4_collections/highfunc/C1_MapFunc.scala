package collections.highfunc

/**
 *
 * map 函数  遍历任意集合中的每个元素进行处理  有返回值   处理完返回结果
 */
object C1_MapFunc {

  def main(args: Array[String]): Unit = {
    val mp = Map("zss" -> 23, "lss" -> 33)
    // 返回处理后的结果数据
    val res: Map[String, Int] = mp.map(tp => (tp._1, tp._2 *10))
    println(res)
    // 打印处理后的结果
    /* for (elem <- res) {
       println(elem)
     }*/
    // 只处理value  key不变
    val res2 = mp.mapValues(v => v + 3)
    println(res2)
  }

}
