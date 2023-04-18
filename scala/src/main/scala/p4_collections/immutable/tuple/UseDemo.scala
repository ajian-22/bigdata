package collections.immutable.tuple

/**
 * 在java中封装数据使用JavaBean ,不够灵活
 * 在scala中有元组可以封装数据 ,灵活 , 嵌套
 * 只能封装最多22个属性
 * 如果元组中只有两个元素  对偶元组
 */
object UseDemo {
  def main(args: Array[String]): Unit = {
    val t1= (78, 988L, "dfsd", 89.01, true)
    println(t1._1)
    println(t1)

    val dtp = ("zss", 23) // 对偶元组
    // 定义一个元组
    val tp = (1, "zss", 23, "M", 10000, "coder",83848,434,"",99.02)
    // 简单的数据结构取值方便
    val age = tp._3 //_n取值  从1开始计数
    val name = tp._2
    // 结构灵活
    val tp2: (String, (Int, String, Int, String)) = ("zss", (1, "zss", 23, "M"))
    val age2 = tp2._2._3
    // 创建一个两个元素的元组
    val tp3: (String, Int) =  Tuple2("zss", 23)
    // 将元组中的元素转换成一个迭代器
    val iterator = tp.productIterator
    // 获取每个元素
    while (iterator.hasNext) {
      println(iterator.next())
    }

    println(tp3)
    println(tp3.swap)
  }
}
