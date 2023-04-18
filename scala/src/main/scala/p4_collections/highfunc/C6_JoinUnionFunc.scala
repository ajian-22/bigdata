package p4_collections.highfunc


object C6_JoinUnionFunc {

  def main(args: Array[String]): Unit = {
    val ls = List(1, 2, 3, 4, 5)
    val arr = Array(3, 4, 5, 6, 7)
    // 交集 在两个集合中同时出现的元素
    //  val ints: Array[Int] = arr.intersect(ls)
    // 差集 在集合1出现在集合2中没有出现的元素
    // val diffValues: List[Int] = ls.diff(arr)
    // 并集 合并两个集合的所有元素  不会去重
    // val unionValues: List[Int] = ls ++ arr
    val unionValues: List[Int] = ls.union(arr).distinct
    unionValues.foreach(println)

  }
}
