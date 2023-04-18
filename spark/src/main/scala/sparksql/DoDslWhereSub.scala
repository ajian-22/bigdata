package sparksql

import org.apache.spark.sql.SparkSession

/**
 * * sparksql可以在sql(编写sql语句)
 * * 还可以在DataFrame上调用api的方式操作数据   DSL
 */
object DoDslWhereSub {

  def main(args: Array[String]): Unit = {
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
       * select
       * *
       * from
       * (
       * select
       * *
       * from
       * t
       * where   name = a
       * ) t2
       * where  ammount > 300
       */
      df
        .where("name = 'a'")
        .select("*")
        .where("ammount > 300")
        .select("*").show()


    }

  }
}
