package sparksql


import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.types.{DataTypes, StructType}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 *  1 创建spark-sql入口实例SparkSession
 *  2 read本地csv结构的数据
 *  3 返回DataFrame对象
 *  4 将 DataFrame注册成视图
 *  5 spark.sql ()  编写sql语句分析结构化数据
 *  printSchema  打印结构
 *  show  展示数据
 */
object SparkSession3 {
  Logger.getLogger("org").setLevel(Level.WARN)

  def main(args: Array[String]): Unit = {
    // 获取SparkSession统一编程入口
    val spark: SparkSession = SparkSession.builder()
      .appName("sql入门示例")
      .master("local[*]")
      .getOrCreate()
    /**
     * 根据数据自定义结构  Schema
     */
    val struct = new StructType()
      .add("uid", DataTypes.IntegerType)
      .add("name", DataTypes.StringType)
      .add("age", DataTypes.IntegerType)
      .add("gender", DataTypes.StringType)
    // 在读取数据的时候指定结构
    val df: DataFrame = spark.read.schema(struct).csv("data/user/user.csv")

    /**
     * root
     * |-- uid: integer (nullable = true)
     * |-- name: string (nullable = true)
     * |-- age: integer (nullable = true)
     * |-- gender: string (nullable = true)
     */
    /**
     * 3,ww,21,F
     * root
     * |-- _c0: string (nullable = true)  id
     * |-- _c1: string (nullable = true) name
     * |-- _c2: int (nullable = true)  age
     * |-- _c3: string (nullable = true)  gender
     *
     * 第一个问题 :  字段的名字 没有语义
     * 第二个问题 :  字段的数据类型 推断的不精准
     */
    df.printSchema()

    /**
     * +---+----+---+------+
     * |uid|name|age|gender|
     * +---+----+---+------+
     * |  1| zss| 23|     M|
     * |  2| lss| 33|     M|
     * |  3|  ww| 21|     F|
     * +---+----+---+------+
     */
    df.show()

  }


}
