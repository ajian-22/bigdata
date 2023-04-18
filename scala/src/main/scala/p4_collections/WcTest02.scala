package p4_collections

import scala.io.Source


object WcTest02 {

  def main(args: Array[String]): Unit = {
    val res = Source.fromFile("/Users/duyaoyao/work/data/wc.txt")
      .getLines()
      .flatMap(_.split(" "))
      .toList
      .groupBy(e => e)
      .map(tp => (tp._1, tp._2.size))
      .toList
      .sortBy(_._2)
      .reverse

    res.foreach(println)

  }
}
