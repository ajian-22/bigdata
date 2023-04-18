package sparksql

import org.apache.spark.sql.types.{DataTypes, StructType}
import utils.SparkUtil

/**
 * CSV : 属性之间使用 , 隔开
 * 可以加载头信息  可以自动类型推断 可以自定义结构
 *
 */
object ReadCsv {
  def main(args: Array[String]): Unit = {
    /**
     * 加载没有头信息的CSV文件
     * 1 字段名 默认
     * 2 数据类型  推断
     */
    val spark = SparkUtil.getSession
    val df = spark.read.csv("spark/data/user/user.csv")
    df.printSchema()

    /**
     * 自动推断数据类型
     */
    val df2 = spark
      .read
      .option("inferSchema", true) //自动推断数据类型
      .csv("spark/data/user/user.csv")
    df2.printSchema()

    /**
     * 加载有头信息的CSV文件
     * root
     * |-- _c0: string (nullable = true)
     * |-- _c1: string (nullable = true)
     * |-- _c2: string (nullable = true)
     * |-- _c3: string (nullable = true)
     */
    val df3 = spark.read.csv("spark/data/user/user2.csv")

    /**
     * 解析第一行信息为 属性字段名
     * root
     * |-- id: string (nullable = true)
     * |-- name: string (nullable = true)
     * |-- age: string (nullable = true)
     * |-- gender: string (nullable = true)
     */
    df3.show(10)
    val df4 = spark.read.option("header", true).csv("spark/data/user/user2.csv")
    df4.show(10)
    df3.printSchema()
    df4.printSchema()
    println("-----------------彩色的分割线--------------------")
    /**
     * 加载有头信息的CSV文件 ;  自动数据类型推导
     * 解析头信息   自动类型推导
     * root
     * |-- id: integer (nullable = true)
     * |-- name: string (nullable = true)
     * |-- age: integer (nullable = true)
     * |-- gender: string (nullable = true)
     */
    val df5 = spark.read
      .option("inferSchema", true)
      .option("header", true)
      .csv("spark/data/user/user2.csv")
    df5.printSchema()

    println("-----------------红色的分割线--------------------")

    // 自定义结构
    val schema = new StructType()
      .add("uid", DataTypes.LongType)
      .add("username", DataTypes.StringType)
      .add("age", DataTypes.IntegerType)
      .add("sex", DataTypes.StringType)
    /**
     * 加载有头信息的CSV文件 ;  自定义Schama
     */
    val df6 = spark.read.schema(schema).option("header", true).csv("spark/data/user/user2.csv")
    df6.printSchema()

    // 将DF输出 , 保存指定数据格式
    //df6.write.parquet("spark/data/parquet/")
    spark.close()

  }

}
