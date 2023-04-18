package collections.mutable.array

import scala.collection.mutable.ArrayBuffer

/**
 * 可变数组  数组的长度可变
 * 所有的可变集合都在 scala.collection.mutable中
 * 1  append 向可变的数组中添加元素
 * 2  remove(index , cnt)  参数1 角标  参数2 删除元素的个数
 * 3 drop(n)  从左边删除n个元素 返回新的集合
 * 4  +=(e) -=(e)  加减指定的元素
 * 5 ++= (arr)  一次添加多个元素
 * 6 clear 清空集合中的所有的元素
 * 7 insert(index,Int*) 在指定的位置插入n个元素
 */
object UseDemo {
  def main(args: Array[String]): Unit = {
    val ab  = ArrayBuffer[Int]()
    println(ab.length)
    ab.append(12)
    ab.append(13)
    ab.append(14)
    ab.append(15)
    println(ab.length)
    for (elem <- ab) {
      println(elem)
    }
    // 删除元素
    println("################")
    // ab.remove(0,2)
    for (elem <- ab) {
      println(elem)
    }
    /*   val ints: mutable.Seq[Int] = ab.drop(3)
        println(ints.toList)*/

    ab += (111)
    println("------")
    println(ab)
    println(ab.toList)
    ab -= (14)
    println(ab.toList)
    ab ++= Array(11,22,33,44,5)
    println(ab.toList)
    // ab.clear()
    println(ab.toList)
    ab.insert(2,1,2,3,4,5,6,7,8,9)
    println(ab.toList)


  }

}
