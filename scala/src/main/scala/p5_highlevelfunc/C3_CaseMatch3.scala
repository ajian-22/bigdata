package p5_highlevelfunc

/**
 * 模式匹配  :判断某种类型 , 值 , 结构是否满足要求
 */
object C3_CaseMatch3 {


  def main(args: Array[String]): Unit = {
    val arr = Array("lny", "wdl", "pjl", "lyf", 12)
    val res: Array[Any] = arr.map(e => {
      e match {
        case x: String => x.toUpperCase
        case _ => None
      }
    })
    res.foreach(println)


  }
}
