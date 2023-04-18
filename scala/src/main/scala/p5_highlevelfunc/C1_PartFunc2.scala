package p5_highlevelfunc

object C1_PartFunc2 {

  def p: PartialFunction[Any, String] = {
    // 类型匹配
    case x: String => x.toUpperCase
  }


  def main(args: Array[String]): Unit = {
    val ls = List(1, 2, "tom", "jim", "cat",true)
    // val res: List[String] = ls.collect(p)
    val res: List[String] = ls.collect({
      case x: String => x.toUpperCase
    })
    println(res)

    val res2 = ls.map(_ match {
      case e: String => e.toUpperCase()
      case e:Int => e+5
      case _ => println("---")
    })
    println(res2)


  }

}
