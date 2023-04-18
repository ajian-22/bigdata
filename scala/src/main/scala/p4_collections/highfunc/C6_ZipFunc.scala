package p4_collections.highfunc


object C6_ZipFunc {

  def main(args: Array[String]): Unit = {
    // 从左到右组成对偶元组  以集合个数少的为依据
    val list1 = List("a", "b", "c", "d", "e")
    val arr1 = Array(1, 2, 3, 4, 5, 6)
    val tuples: List[(String, Int)] = list1.zip(arr1)
    println(tuples)
    tuples.foreach(println)
    println("###############################")
    val list2 = List("a", "b", "c", "d", "e")
    val index: List[(String, Int)] = list2.zipWithIndex
    index.foreach(println)
    println("###############################")

    val list3 = List("a", "b", "c", "d", "e")
    val list4 = List("zss", "lss", "ww", "zl", "tq")
    val res: List[((String, String), Int)] = list3.zip(list4).zipWithIndex
    res.foreach(println)
    println("###############################")
    val list5 = List("a", "a", "b", "c", "d", "e")
    val res2: List[(String, Int)] = list5.zipWithIndex
    res2.foreach(println)
    println("###############################")
    val map: Map[String, Int] = res2.toMap
    //val list: List[(String, Int)] = map.toList
    // Map的key不能重复  Map集合不能保证数据的顺序
    map.keys.foreach(println)


  }

}
