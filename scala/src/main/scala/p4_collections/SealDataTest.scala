package p4_collections

import p2_object.User2

import scala.io.Source


object SealDataTest {

  def main(args: Array[String]): Unit = {
    val users = Source.fromFile("d://user.txt")
      .getLines()
      .map(line => {
        val arr = line.split(",")
        User2(arr(0).toInt, arr(1), arr(2).toInt, arr(3).toDouble)
      })

    val br=Source.fromFile("d://user.txt")
       br
      .getLines()
      .flatMap(_.split(","))
      .foreach(println)

    br.close()

  }


}
