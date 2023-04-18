package sparksql

import org.apache.spark.sql.SparkSession


object ReadJson {
  def main(args: Array[String]): Unit = {
    // 获取SparkSession统一编程入口
    val spark: SparkSession = SparkSession.builder()
      .appName("sql入门示例")
      .master("local[*]")
      .getOrCreate()

    /**
     * json有标准的数据结构
     * 属性
     *   数据类型
     *  json数据本身具有结构 , 自动的根据json数据格式 创建StructType
     *  不用自定义结构
     *
     *  1  加载所有数据中包含的字段   作为结构
     *  2  如果有脏数据  当前行多出一个不为null的_corrupt_record字段值
     */
    val df = spark.read.json("spark/data/json")
    //  df.select("sid").where("sid>3").groupBy().sum()

    /**
     * root
     * |-- age: double (nullable = true)
     * |-- gender: string (nullable = true)
     * |-- sid: long (nullable = true)
     * |-- sname: string (nullable = true)
     */
    df.printSchema()
    df.show()

    //df.createTempView("tb_stu")

    /*    spark.sql(
          """
            |select
            |gender  as gender,
            |avg(age) as avg_age
            |from
            |tb_stu
            |group by gender
            |
            |
            |""".stripMargin).show()*/


    spark.close()

  }



}
