package sparksql

import org.apache.spark.sql.SparkSession

/**
 * * sparksql可以在sql(编写sql语句)
 * * 还可以在DataFrame上调用api的方式操作数据   DSL
 */
object DoDslRenameCol {

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
    /**
     * 字段重命名
     * 1  selectExpr  解析语义
     * 2  字段对象  .as
     * 3  withColumnRenamed 修改制定的字段的值
     * 4  toDF 制定所有字段的名字    tp-->DF
     */

    // df.selectExpr("name  as shop_name" , "dt as  date" , "ammount as money")
    //df.select('name.as("shop_name"), $"dt".as("date") ,df.col("name").as("shopName), col("ammount").as("money"))
    /* df.select("name" , "dt")
       .withColumnRenamed("name" , "shop_name")
       .withColumnRenamed("dt" , "date")*/
    // 修改所有字段耳朵字段名    一般用于  元组  DF的指定名称
    df.toDF("shop_name" , "date" , "money")
      .show()



  }

}
