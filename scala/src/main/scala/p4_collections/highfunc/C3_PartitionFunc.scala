package collections.highfunc

/**
 * partition 按照条件分组
 * 两组 1 符合条件的1组  2 不符合条件的1组
 */
object C3_PartitionFunc {

  def main(args: Array[String]): Unit = {
    val list = List(1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9)
    //将集合分成两组  符合要求的在前面位置_1  不符合要求在后面 _2
    val tuple: (List[Int], List[Int]) = list.partition(_ >= 4)
    println(tuple._1)
    //Map集合中  注意Key不能重复
    val mp = Map[String, String]("sl" -> "lny", "sl" -> "swk", "sl" -> "zbj", "em" -> "shitai")
    println(mp.size)
    val tuple1: (Map[String, String], Map[String, String]) = mp.partition(_._1.equalsIgnoreCase("sl"))
    // 按照元素内容分区  9
    val map: Map[Int, List[Int]] = list.groupBy(e => e)

    val mp2: Map[Boolean, List[Int]] = list.groupBy(e => e % 2 == 0)

    val opt: Option[List[Int]] = map.get(9)
    if (opt.isDefined) println(opt.get)


  }
}