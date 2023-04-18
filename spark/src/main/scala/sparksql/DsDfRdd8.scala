package sparksql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession

/**
 * Created by duyaoyao on 2022/7/16 下午10:01
 *
 * goal:
 *
 * way :
 *
 * key :
 *
 * summary :
 */

case class  Person(id :Int,name:String)
object DsDfRdd8 {
  def main(args: Array[String]): Unit = {

    val spark = SparkSession.builder()
      .master("local[*]")
      .appName("show")
      //.enableHiveSupport()
      .getOrCreate()
    import spark.implicits._
    val rdd: RDD[Person] = spark.sparkContext.parallelize(Seq(Person(1, "dyy"), Person(2, "bozi")))
    val df = rdd.toDF
    df.show()
    df.printSchema()
    spark.close()



  }}
