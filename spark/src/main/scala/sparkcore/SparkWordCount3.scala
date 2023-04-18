package sparkcore

import org.apache.spark.{SparkConf, SparkContext}

/**
 */
object SparkWordCount3 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      //.setMaster("local")
      .setAppName(this.getClass.getSimpleName.stripSuffix("$"))

    val sc = new SparkContext(conf)

    sc.textFile("hdfs:///data/spark", 3).flatMap(_.split(" ")).map((_, 1)).reduceByKey(_ + _).sortBy(- _._2).take(20).toList.foreach(println)

    sc.stop()
  }
}
