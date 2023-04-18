package p5_highlevelfunc

/**
 * 偏函数是一种特质 , 用来处理某种数据类型
 */
object C1_PartFunc {

  // 定义一个偏函数
  val p = new PartialFunction[Any, String] {
    // 执行的时候是每个元素调用一次  判断每个元素是否是某种数据类型  String
    override def isDefinedAt(x: Any): Boolean = {
      x.isInstanceOf[String]
    }

    // 上一个方法返回true 这个方法才会执行
    override def apply(v1: Any): String = {
      v1.asInstanceOf[String].toUpperCase
    }
  }

  def main(args: Array[String]): Unit = {
    val ls = List(1, 2, "tom", "jim", "cat")
    // 1 守卫模式和推导式
    val res44 = for (elem <- ls if elem.isInstanceOf[String]) yield {
      elem.asInstanceOf[String].toUpperCase
    }
    res44.foreach(println)
    // 2 过滤
    val res: List[String] = ls.filter(_.isInstanceOf[String]).map(_.asInstanceOf[String].toUpperCase)
    println(res)


    println("--------")
    //val res44 = ls.map(p)
    //println(res44)
    val res2: List[String] = ls.collect(p)
    println(res2)


  }

}
