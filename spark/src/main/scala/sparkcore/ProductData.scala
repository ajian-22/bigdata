package sparkcore

import java.io.{File, PrintWriter}
import java.util.Random


object ProductData {
  def main(args: Array[String]): Unit = {

    val data = List("scala flink python bigdata sql", "sql flink bigdata scala scala", "hive hdfs hdfs hive scala spark")

    val writer = new PrintWriter(new File("/Users/duyaoyao/work/data/sparkwc_s.txt"))
    val random = new Random()
    for (i <- 1 to 10000) {
      val idx = random.nextInt(2)
     // println(data(idx))
      writer.write(data(idx))
    }
    writer.close()
  }


}
