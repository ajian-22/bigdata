package sparkcore

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

object SuanZiTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf()
      .setMaster("local[3]")
      .setAppName(this.getClass.getSimpleName.stripSuffix("$"))
    val sc = new SparkContext(conf)

    val rdd1 = sc.parallelize(List(1,2,3,4,5,6,7,8,9), 3)
    val rdd2: RDD[Int] = rdd1.map(_ + 3)


    rdd2.persist()
    rdd2.count() //action 1  job1
    rdd2.foreach(println)  //action2   job2
    rdd2.collect()
    rdd2.unpersist()

    Thread.sleep(Long.MaxValue)
    sc.stop()
  }
}