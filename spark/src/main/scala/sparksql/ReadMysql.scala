package sparksql

import java.util.Properties

import utils.SparkUtil

/**
 * 1 导入MySQL的依赖
 * 2 调用read.jdbc
 *
 */
object ReadMysql {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSession
    val props = new Properties()
    props.setProperty("user", "root")
    props.setProperty("password", "root123U")
    val df = spark.read.jdbc("jdbc:mysql://bigdata001:3306/dolphinscheduler", "t_ds_process_definition", props)
    // 统计北京数据条数
    df.createTempView("ds")
    val resDf = spark.sql(
      """
        |select *
        |from
        |ds
        |""".stripMargin)

    resDf.printSchema()
    resDf.show(30,false)

    spark.close()

  }

}
