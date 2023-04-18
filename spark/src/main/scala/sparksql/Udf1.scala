package sparksql

import utils.SparkUtil


object Udf1 {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSession
    //parquet
    val df = spark.read.parquet("spark/data/parquet/")

    /**
     * +---+--------+---+---+
     * |uid|username|age|sex|
     * +---+--------+---+---+
     * |  1|     zss| 23|  M|
     * |  2|     lss| 33|  M|
     * |  3|      ww| 21|  F|
     * +---+--------+---+---+
     * 创建视图
     */
    df.createOrReplaceTempView("tb_user")

    /**
     *   自定义函数  逐行
     *   1 定义一个scala函数
     *   2  注册
     *   3 使用
     */
    // 定义
    val f =  (username:String) =>{
      "HELLO:" +username.toUpperCase()
    }
    // 注册
    spark.udf.register("love",f)

    /**
     * 聚合运算   UDAF
     */


    spark.sql(
      """
        |select
        |uid ,
        |username ,
        |age ,
        |sex ,
        |love(username)      --  函数处理  每个名字  HELLO:ZSS
        |from
        |tb_user
        |
        |""".stripMargin).show()
  }


}
