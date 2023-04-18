package collections.immutable.map

object UseDemo {
  def main(args: Array[String]): Unit = {
    // 不可变的Map集合定义
    val mp= Map("zss" -> 23, "lss" -> 23, "ny" -> 48)

    val mp2 = Map[String, Int](("zss", 23), ("lss", 33), ("ny", 48))
    mp.size
    // 根据key获取value值
    val age = mp.getOrElse("ny", 0)
     println(age)
    // Map集合遍历
    // 方式1  根据keys遍历
    mp.head
    val keys = mp.keys
    // mp.keySet
    // 根据key获取value
    for (key <- keys) {
      val v = mp.get(key)
      println(s"Mp集合的key是: $key value是: $v")
    }
    // Map集合中是对偶元组
    // 方式2  直接遍历出 集合中的元素{对偶元组}
    for (elem <- mp) {
      println(s"key: ${elem._1}---Value: ${elem._2}")
    }
    println("########################")
    // 使用(k,v) 接收对偶元组
    for ((k, v) <- mp) {
      println(s"key: $k---Value: $v")
    }

    for ((k, _) <- mp) {
      println(s"key: $k")
    }

    for ((_, v) <- mp) {
      println(s"V: $v")
    }
    // 获取所有的value数据
    mp.values
    // 打印mp中的所有key
    mp.foreach(map => println(map._1))
    mp.foreach(map => println(map._2))
    // 将key大写  v*10
    val res: Map[String, Int] = mp.map(tp => {
      (tp._1.toUpperCase(), tp._2 * 10)
    })
    // 打印
    res.foreach(tp => println(s"K:${tp._1}   V:${tp._2}"))


  }

}
