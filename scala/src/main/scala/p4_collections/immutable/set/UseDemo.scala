package collections.immutable.set

/**
 * 1 去重
 * 2 可变和不可变的名字一样  使用包区分
 * 3 没有顺序
 */
object UseDemo {

  def main(args: Array[String]): Unit = {
    // 不可变的Set去重
    val set = Set[Int](5, 1, 2, 3, 4, 3, 4, 5, 5)
    println(set)

    set.size
    // 添加一个元素  返回新的集合
    val set2 = set.+(10)
    // 返回新的空集合
    val empty: Set[Int] = set.empty
    println(empty.toList)
    // val res = set.filter(e => e % 2 == 1)
    val res = set.filter(_ % 2 == 1)
    println(set)
    val last: Int = set.last
    println(last)

  }
}
