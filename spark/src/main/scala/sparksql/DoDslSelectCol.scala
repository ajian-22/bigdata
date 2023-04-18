package sparksql

import org.apache.spark.sql.SparkSession

/**
 * * sparksql可以在sql(编写sql语句)
 * * 还可以在DataFrame上调用api的方式操作数据   DSL
 */
object DoDslSelectCol {

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
    import session.implicits._
    import org.apache.spark.sql.functions._

    // df.selectExpr("upper(name) as  upper_name" , "substr(dt , 0 , 7)" , "ammount+10 as amount").show()
    df.select(upper('name), substring('dt, 0, 7), 'ammount.plus(10)).show()

    /**
     * 在字段对象上可以再次调用处理方法
     */
    // df.select("name").show()
    /*    df.select(df("name").as("shop_name")).show()
        df.select(df.col("name").as("shop_name")).show()
       // 隐式函数 参数 类
       import  session.implicits._
       df.select($"name".as("shop_name"))
       df.select('name.as("shop_name"))
       */

  }

}
