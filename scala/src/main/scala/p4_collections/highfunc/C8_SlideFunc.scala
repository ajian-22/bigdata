package p4_collections.highfunc

object C8_SlideFunc {

  def main(args: Array[String]): Unit = {
    val arr = Array("a", "b", "c" ,"d","e","f")
    // 起始角标  结束角标  [1,3) -->  1,2
    val res1: Array[String] = arr.slice(1, 3)
    res1.foreach(println)
    println("####################")
    // 默认的步进为1  查看大小指定为2
    val res2: Iterator[Array[String]] = arr.sliding(2)
    res2.foreach(arr=>println(arr.toList))
    println("####################")
    // 参数一  窗口大小 参数二  步进  当大小和步进一致 属于滚动数据只处理一次
    val res3: Iterator[Array[String]] = arr.sliding(2,2)
    res3.foreach(arr=>println(arr.toList))
  }

}
