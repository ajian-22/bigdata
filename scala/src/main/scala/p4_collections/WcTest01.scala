package p4_collections

import scala.io.Source


object WcTest01 {

  def main(args: Array[String]): Unit = {
    //val path = "/Users/duyaoyao/work/data/wc.txt"
    val res = Source.fromFile("/Users/duyaoyao/work/data/wc.txt").getLines().flatMap(_.split(" ")).map((_, 1)).toList.groupBy(_._1).map(tp => (tp._1, tp._2.size)).toList.sortBy(- _._2)
    //    // 打印结果
    res.foreach(println)

  }

}
