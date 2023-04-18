package sparksql
import org.apache.spark.sql.SparkSession

/**
 * * sparksql可以在sql(编写sql语句)
 * * 还可以在DataFrame上调用api的方式操作数据   DSL
 */
object DoDsl {

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
     * 使用  TableAPI
     * select name , ammount
     * from tb_shop
     * where  ammount > 300
     * order  by name  desc , ammount asc
     */
    df
      .where("ammount > 300")
      .sort(df.col("name").desc, df.col("ammount").asc)
      .select("name", "ammount")
      .show()

  }

}
