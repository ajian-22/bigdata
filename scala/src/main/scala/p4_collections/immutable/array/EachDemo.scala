package collections.immutable.array

/**
 * 1 for
 * 2 角标
 * 3 map
 * 4 foreach
 * 处理数组中的每个元素
 * 使用map方法遍历  f:Int=> B   block 有返回值
 * foreach        f:Int=> U   没有返回值
 */
object EachDemo {
  def main(args: Array[String]): Unit = {

    val arr = Array[Int](6,9,3,4,5)
    val res: Array[Int] = arr.map(_ * 10)
    println(res.toList)

    // 遍历数组中的每个元素
    arr.foreach(println)
    // 处理数组中的每个元素
    //  arr.map(e=>println(e))
    // arr.map(println(_))

    /*   for (elem <- arr) {
         println(elem)
       }
      for(i <- 0 to arr.length-1){
      arr(i)
      }
      for(i <- 0 until  arr.length){}

       */

    for(idx <- arr.indices){
      println(s"index:$idx and value:${arr(idx)}")
    }

  }


}
