package collections.highfunc



/**
 * 对集合中的数据进行条件筛选
 * 1 if 判断
 * 2 守卫模式
 * 3 filter过滤集合的方法  遍历集合中的每个元素  符合条件的返回true 返回true的元素 收集起来返回
 * 4 多条件过滤 可以使用多个filter函数 也可以使用 &&
 */
object C2_FilterFunc {
  def main(args: Array[String]): Unit = {
    // 定义一个集合  数据类型 Any
    val ls = List(1, 2, 3, 4, "java", "scala")
    // 使用守卫模式  过滤出数值  使用 推导式收集(处理后)结果
    // 高类型转低类型  asInstanceOf  Any->Int
    val list = for (elem <- ls if elem.isInstanceOf[Int]) yield elem.asInstanceOf[Int] * 10
    // 打印结果
    println(list)
    println("#############################")
    // 对数据条件的判断    返回符合条件的数据
    val res1 = ls.filter(e => e.isInstanceOf[Int])
    // map 有返回值  处理符合条件的数据
    val res2 = res1.map(e => e.asInstanceOf[Int] * 10)
    println("#############################")
    val arr = Array[Int](1, 2, 3, 4, 5, 6, 7, 8, 9)
    // 返回所有的奇数
    val res00 = arr.filter(_ % 2 == 0).toList
    println("------")
    println(res00)
    // 大于5的奇数
    println(arr.filter(_ % 2 == 1).filter(_ > 5).toList)
    println(arr.filter(e => e % 2 == 1 && e > 5).toList)
    // 小于等于3
    println(arr.filterNot(_ > 3).toList)

    // 定义一个Map
    val mp = Map[String, Int]("zss" -> 23, ("lss", 33), ("lny", 49))
    /* val mpRes = mp.filter(_._1.contains("s"))
     println(mpRes)*/
    val mpRes = mp.filter(_._2 > 30)
    println(mpRes)


  }

}
