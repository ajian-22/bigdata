package sparksql

import java.util.Properties

import org.apache.spark.sql.{SaveMode, SparkSession}

object SaveDfFileDbHiveTable {

  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder()
      .master("local[*]")
      .appName("show")
      .getOrCreate()
    val  df = session.read.option("header" , true).option("inferSchema",true).csv("data/user/user3.csv")
    // 写出指定的文件格式
    //  df.write.parquet("data/parquet")
    // 默认使用 parquet保存
    // df.write.save("data/table")
    // df.write.text("data/text")

    /**
     * 将数据输出到mysql
     *  自动的建表
     */
    val url = "jdbc:mysql://localhost:3306/cool?characterEncoding=UTF-8"
    val tableName = "tb_user"
    val prop = new Properties()
    prop.setProperty("user" , "root")
    prop.setProperty("password" , "root")


    //df.write.jdbc(url ,tableName ,prop)
    //设置数据的保存模式
    /**
     * Append,  追加
     * Overwrite,  覆盖
     * ErrorIfExists,  数据存在  异常
     * Ignore  数据存在  忽略
     *
     */
    df.write.mode(SaveMode.Append).jdbc(url ,tableName ,prop)

    //参考ReadHive2
    //  resDF.write.mode(SaveMode.Append).saveAsTable("default.show_res")
    // resDF.write.partitionBy("dt").mode(SaveMode.Append).saveAsTable("default.shop_res_dt")
    //resDF.write.option("orc.compress", "zlib").format("orc").partitionBy("dt").mode(SaveMode.Append).saveAsTable("default.shop_res_dt3")


  }
}
