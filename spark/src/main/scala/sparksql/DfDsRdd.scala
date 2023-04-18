package sparksql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{DataTypes, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}

/**
 * 有些特殊的需求不好使用 SQL实现  这个时候可以阿静DF  DS转换成 RDD  使用算子实现
 */
object DfDsRdd {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    val session = SparkSession.builder()
      .master("local[*]")
      .appName("show")
      .enableHiveSupport()
      .getOrCreate()

    val stuSchema = new StructType()
      .add("id", DataTypes.IntegerType)
      .add("name", DataTypes.StringType)
      .add("gender", DataTypes.StringType)
      .add("age", DataTypes.IntegerType)

    val frame1: DataFrame = session.read.schema(stuSchema).csv("spark/data/stu/student.txt")
    /**
     * 将 DataFrame转换成RDD  编程
     */
    val rdd: RDD[Row] = frame1.rdd

    val rddData: RDD[(Int, String, Int, String)] = rdd.map(row => {
      // 解析结构  获取 字段  分组
      val id = row.getAs[Int]("id")
      val name = row.getAs[String]("name")
      val gender = row.getAs[String]("gender")
      val age = row.getAs[Int]("age")
      (id, name, age, gender)
    })

    val groupedRDD: RDD[(String, Iterable[(Int, String, Int, String)])] = rddData.groupBy(_._4)

    val resRDD = groupedRDD.map(tp => {
      (tp._1, tp._2.map(_._3).sum / tp._2.size)
    })
    resRDD.foreach(println)
  }

}
