package collections.mutable.map

import scala.collection.mutable

object UseDemo {
  def main(args: Array[String]): Unit = {
    // 不可变的Map集合定义
    val mp: Map[String, Int] = Map("zss" -> 23, "lss" -> 23, "ny" -> 48)
    val mp2 = mutable.Map[Int, Int]()
    mp2.+=((1,11))
    println(mp2.size)
    mp2.put(2,2)
    println(mp2.size)
    mp2.remove(1)
    println(mp2.size)
    mp2.clear()
    mp2.empty


  }

}
