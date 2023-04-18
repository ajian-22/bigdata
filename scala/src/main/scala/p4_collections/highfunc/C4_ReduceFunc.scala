package collections.highfunc

/**
 * reduce  从左边开始计算  reduceLeft
 */
object C4_ReduceFunc {

  def main(args: Array[String]): Unit = {

    val list = List(1, 3, 5, 7, 9)
    //(1+3)+5)+7)+9
    //val res: Int = list.reduce((x1, x2) => x1 + x2)
    //println(res)

     val res: Int = list.reduce(_ + _)
    // val res: Int = list.reduce(_ * _)
    //(1-3)-5)-7)-9
    val res2: Int = list.reduce(_ - _)
    println(res)

    val ls = List[String]("tom", "jim", "jack", "rose", "naiyuan")
    // val str: String = ls.reduce((x1, x2) => x1 + " " + x2)
    // 字符串的拼接
    val str: String = ls.reduce(_ + " " + _)
    val str1: String = ls.mkString("["," ","]")
    println(str1)

    val map = Map(("shaolin", 88), ("emei", 77), ("wudang", 99))
    println(map.reduce(
      (mp1, mp2) => ((mp1._1 + "-" + mp2._1).toUpperCase, mp1._2 + mp2._2)
    ))

    //reduceLeft
   // val res2: Int = list.reduceLeft(_ - _)
    //println(res2)

    //reduceRight
    val list3 = List(1, 3, 5, 7, 9)
    //val res3: Int = list3.reduceRight(_ + _)
    // 1-(3-(5-((7-9)))   5
    val res3: Int = list3.reduceRight((x1, x2) => x1 - x2)
    println(res3)


  }
}
