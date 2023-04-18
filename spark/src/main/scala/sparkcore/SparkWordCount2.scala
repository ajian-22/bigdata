package sparkcore

import org.apache.spark.{SparkConf, SparkContext}

/**
 */
object SparkWordCount2 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local")
      .setAppName("wc2")

    val sc = new SparkContext(conf)

    // rdd -> partition -> line
    sc.textFile("file:///Users/duyaoyao/work/data/wc.txt", 3)
      .flatMap(_.split("\\s+"))
      .map((_, 1))
      .map(tp2 => (tp2._1, tp2._2 * 10))
      //划分
      .reduceByKey(_ + _)
      .sortBy(-_._2)
      .foreach(println)

    Thread.sleep(100000000L)
    sc.stop()
  }

}
