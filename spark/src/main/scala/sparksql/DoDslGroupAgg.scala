package sparksql

import org.apache.spark.sql.SparkSession

/**
 * * sparksql可以在sql(编写sql语句)
 * * 还可以在DataFrame上调用api的方式操作数据   DSL
 */
object DoDslGroupAgg {

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    val session = SparkSession.builder()
      .master("local[*]")
      .appName("show")
      .enableHiveSupport()
      .getOrCreate()

    // 加载hive表中的数据
    val df = session.sql(
      """
        |select * from tb_shop
        |""".stripMargin)

    df.groupBy("name").count().show()
    df.groupBy("name").sum("ammount").show()
    df.groupBy("name").max("ammount").show()
    df.groupBy("name").min("ammount").show()
    df.groupBy("name" ,"dt").avg("ammount").show()



    /**
     * agg  分组后的聚合操作
     * agge( (字段, 聚合函数) , (字段 , 聚合函数) ,( 字段 , 聚合函数) ...  )
     *
     */
    df.groupBy("name").agg(("ammount","sum") , ("ammount", "max") ,("dt" ,"max")).show()


  }
}
