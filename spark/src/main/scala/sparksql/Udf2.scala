package sparksql

import org.apache.spark.sql.expressions.Aggregator
import org.apache.spark.sql.{Encoder, Encoders}
import utils.SparkUtil

/**
 * 在Spark.functions中有大量的实现函数
 * 一般不需要自定义函数 , 特殊的需求需要自定义函数
 *
 *
 */
object Udf2 {
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
     * 平均年龄
     * 接收多个Int  返回一个Double
     *   聚合函数   集成指定的聚合类
     */
    // 注册聚合函数
    //spark.udf.register("myavg",udaf(new MyAvgFunction))


    spark.sql(
      """
        |select
        |myavg(age)
        |from
        |tb_user
        |""".stripMargin).show()

  }

}

/**
 * 存储中间数据的类
 * @param sum
 * @param cnt
 */
case class  Buff(sum:Int , cnt:Double)
/**
 * 自定义聚合函数类
 * 泛型1   输入数据
 * 泛型2    中间的缓存数据
 * 泛型3   输出结果数据
 */
class MyAvgFunction extends  Aggregator[Int ,Buff , Double]{
  /**
   * 初始值
   * @return
   */
  override def zero: Buff = Buff(0,0)

  /**
   * 分区内计算逻辑
   * @param b
   * @param a
   * @return
   */
  override def reduce(b: Buff, a: Int): Buff = {
    val sum =  b.sum+a
    val cnt =  b.cnt+1
    Buff(sum,cnt)
  }

  /**
   * 分区间的计算逻辑
   * @param b1
   * @param b2
   * @return
   */
  override def merge(b1: Buff, b2: Buff): Buff = {
    Buff(b1.sum+b2.sum,b1.cnt+b2.cnt)
  }

  /**
   * 最终的计算结果
   * @param reduction
   * @return
   */
  override def finish(reduction: Buff): Double ={
    reduction.sum/reduction.cnt
  }

  /**
   * 这里是rdd  数据来自sql---rdd  返回sql
   * @return
   */
  override def bufferEncoder: Encoder[Buff] = Encoders.product

  override def outputEncoder: Encoder[Double] = Encoders.scalaDouble
}