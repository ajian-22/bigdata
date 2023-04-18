package sparksql

import org.apache.spark.sql.SparkSession

/**
 * * sparksql可以在sql(编写sql语句)
 * * 还可以在DataFrame上调用api的方式操作数据   DSL
 */
object DoDslWhere {

  def main(args: Array[String]): Unit = {
    def main(args: Array[String]): Unit = {
      System.setProperty("HADOOP_USER_NAME", "root")
      val session = SparkSession.builder()
        .master("local[*]")
        .appName("show")
        .enableHiveSupport()
        .getOrCreate()

      import session.implicits._

      // 加载hive表中的数据
      val df = session.sql(
        """
          |select * from tb_shop
          |""".stripMargin)

      // 解析sql语义
      /*   val ds = df.where("name  = 'a' and  ammount > 300")
         val ds2 = ds.select("name", "ammount")
         ds2.show()*/
      // === 比较值  还比较数据类型
      df.where('ammount > 300 and $"name" === "a").show()

    }

  }
}
