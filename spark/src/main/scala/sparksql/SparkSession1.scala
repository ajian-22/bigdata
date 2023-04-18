package sparksql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.{DataFrame, SparkSession}

/**
 * 1 创建spark-sql入口实例SparkSession
 * 2 read本地csv结构的数据
 * 3 返回DataFrame对象
 * 4 将 DataFrame注册成视图
 * 5 spark.sql ()  编写sql语句分析结构化数据
 * printSchema  打印结构
 * show  展示数据
 */
object SparkSession1 {
  Logger.getLogger("org").setLevel(Level.WARN)

  def main(args: Array[String]): Unit = {
    // 创建一个sparksql的编程入口
    val spark = SparkSession
      .builder()
      .appName(this.getClass.getSimpleName.stripSuffix("$"))
      .master("local[*]")
      .getOrCreate()

    //SparkSession(包含sparkcontext，也包含sqlcontext)
    //val sc = spark.sparkContext
    //val sql = spark.sqlContext

    // 加载json数据文件为dataframe
    val df: DataFrame = spark.read.json("spark/data/json/people.dat")

    // 打印df中的schema元信息
    df.printSchema()

    // 打印df中的数据
    df.show(20, false)

    // 在df上，用调api方法的形式实现sql
    df.where("age > 30").show()

    // 将df注册成一个“表”（视图），然后写原汁原味的sql
    df.createOrReplaceTempView("people")
    //spark.sql("select * from people where age > 30 order by age desc").show()
    spark.sql(
      """
        |select *
        |from people
        |where age > 30
        |order by age desc
        |""".stripMargin).show()

    //释放资源
    spark.close()
  }


}
