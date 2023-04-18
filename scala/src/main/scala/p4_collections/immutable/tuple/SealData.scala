package collections.immutable.tuple

import scala.io.Source

/**
 * u001,zss,23,M
 * u002,lss,22,F
 * u003,lls,33,M
 * u004,cls,43,F
 * 使用元组封装简单的数据
 * Source.fromFile("d://user.txt")  本地加载数据
 * source.getLines()  使用迭代器迭代文件中的行数据
 */
object SealData {
  def main(args: Array[String]): Unit = {
    // 读取本地文件
    val source = Source.fromFile("C:\\Users\\yaoyaodu\\Documents\\pic\\testdata\\user.txt")
    val lines: Iterator[String] = source.getLines()
    // 获取行数据
    val users = lines.map(line => {
      // 处理行  封装
      val arr = line.split(",")

      (arr(0), arr(1), arr(2).toInt, arr(3))
    })
    //lines.foreach(println)

    // 存储在集合中
    val ls = users.toList
    // 求几个人的平均年龄
    val avg_age = ls.map(_._3).sum / ls.size
    println(avg_age)

    /*for (elem <- ls) {
      println(elem)
    }

    val ages: immutable.Seq[Int] = ls.map(user => user._3)
    val avg_age = ages.sum/ages.size*/

  }
}
