package collections.immutable.option

object UseDemo {
  def main(args: Array[String]): Unit = {
    // 不可变的Map集合定义
    val mp: Map[String, Int] = Map("zss" -> 23, "lss" -> 23, "ny" -> 48)
    // 根据key获取map集合的value值
    // 可能有 可能没有
    // Option 封装了上面的两种可能  有 Some(v)  没有 None
    val res: Option[Int] = mp.get("zss")
    if(res.isDefined){  //Some 说明有值
      println(res.get)
    }else{
      println("没有key")
    }
    // 使用这个操作
    val i: Int = mp.getOrElse("zss", 0)

  }

}
