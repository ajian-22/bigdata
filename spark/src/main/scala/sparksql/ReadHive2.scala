package sparksql

import org.apache.spark.sql.SparkSession

/**
 * 加载hive中的数据
 * 分析统计
 * 将结果保存在 hive中  , mysql中
 * 加载hive中的数据
 * 将结果保存在hive中/mysql
 * 指定分区          ***
 * 指定数据存储格式    ***
 * 指定数据压缩格式
 *
 */
object ReadHive2 {
  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    val session = SparkSession.builder()
      .master("local[*]")
      .appName("show")
      .enableHiveSupport()
      .getOrCreate()
    /**
     * 每个店铺最大连续销售天数
     * 每个人一个月内 最大连续登录天数
     */
    val resDF = session.sql(
      """
        |select
        |    name ,
        |    max(cnt) as   max_cnt ,
        |    '2022-07-13' as dt
        |    from
        |(
        |    SELECT
        |        name ,
        |        count(1) as  cnt
        |    from
        |    (
        |        select
        |            * ,
        |            date_sub(dt , row_number()  over(partition by name order by dt)) as diff
        |        from
        |            tb_shop
        |    )  t1
        |    group  by  name , diff
        |) t2
        |group by name  ;
        |""".stripMargin)

    /**
     * 2022-07-13  分区中
     * 将处理后的结果数据存储在mysql中
     * 指定存储文件格式format 默认是 parquet  默认的压缩格式是 snappy
     *
     */
    //  resDF.write.mode(SaveMode.Append).saveAsTable("default.show_res")
    // resDF.write.partitionBy("dt").mode(SaveMode.Append).saveAsTable("default.shop_res_dt")
    //resDF.write.option("orc.compress", "zlib").format("orc").partitionBy("dt").mode(SaveMode.Append).saveAsTable("default.shop_res_dt3" + "")


  }

}
