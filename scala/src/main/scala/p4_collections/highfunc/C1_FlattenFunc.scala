package p4_collections.highfunc

object C1_FlattenFunc {
  def main(args: Array[String]): Unit = {
    val dataSource: List[Array[String]] = List(Array("doris", "spark"), Array("doris", "hive", "hadoop"))
    // list("doris", "spark","doris", "hive", "hadoop")

    val res: List[String] = dataSource.flatten
    println(res)




  }


}
