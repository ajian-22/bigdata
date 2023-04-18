package sparksql

import org.apache.spark.sql.{DataFrame, SparkSession}


object ReadHive {
  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    val spark = SparkSession
      .builder()
      .master("local[*]")
      .appName("获取 spark-sql环境")
      // 开启对hive的支持
      .enableHiveSupport()
      .getOrCreate()
      spark.sql("show databases").show()
    // 直接加载hive中的表

    //spark.sql("use gupao")
    val table_name="gupao.action"
    val df: DataFrame = spark.sql(s"select  * from  $table_name")

    df.printSchema()
    df.show()
    spark.close()
  }
}
