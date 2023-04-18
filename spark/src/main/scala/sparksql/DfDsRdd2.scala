package sparksql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.types.{DataTypes, StructType}
import org.apache.spark.sql.{DataFrame, Row, SparkSession}


object DfDsRdd2 {

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

    val frame1: DataFrame = session.read.schema(stuSchema).csv("data/stu/student.txt")
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

    /**
     * 解析Row数据
     */
    rdd.map(row => {
      val id = row.get(0).asInstanceOf[Int]
      val name = row.get(1).asInstanceOf[String]
      val gender = row.getString(2)
      val age = row.getInt(3)
      (id, name, gender, age)
    })

    println("===============================")

    /**
     * 直接模式匹配 Row结构
     */
    rdd.map( {
      case Row(id: Int, name: String, gender: String, age: Int) => {
        (id, name, age, gender)
      }
    }).foreach(println)


  }

}
