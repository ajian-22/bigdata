package sparksql

import org.apache.spark.sql.SparkSession

/**
 * * sparksql可以在sql(编写sql语句)
 * * 还可以在DataFrame上调用api的方式操作数据   DSL
 */
object DoDslSelect {

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
     * select(col:String , cols:String*)
     * select(col:Column)    字段对象 可以调用方法处理字段
     * selectExpr(expr:String*)  可以解析字符串 语义  比如  name as shopname   ;  upper(name)   ; id*10  ; ....
     */
    df.select("name" , "dt" , "ammount","upper(name) as uname ")  // 直接获取字段
    df.select(df.col("name").as("shop_name") , df.col("ammount").plus(100).alias("ammount"))
    //
    df.selectExpr("upper(name) as shop_name" , "dt" , "ammount*100")
      .show()

  }

}
