package sparksql

import org.apache.spark.rdd.RDD
import utils.SparkUtil


object CreateDfRddTuple {

  def main(args: Array[String]): Unit = {
    val session = SparkUtil.getSession
    // 获取RDD
    val sc = session.sparkContext

    val rdd: RDD[String] = sc.textFile("spark/data/teacher/")
    // 元组类型的RDD
    val tpRDD: RDD[(String, String, Int, String, Double)] = rdd.map(line => {
      val arr = line.split(",")
      //2,星哥,46,M,180
      (arr(0), arr(1), arr(2).toInt, arr(3), arr(4).toDouble)
    })
    // 创建DF 1) javaBean  2) scalabean  3) Row  4)元组
    /**
     * 元组 创建DataFrame
     * 1) 具有数据类型
     * 2) 属性名 不好  _1 _2 _3
     * 3) 给不友好的字段名  重新命名toDF 重新给所有的字段命名
     */
    val df = session.createDataFrame(tpRDD)
    val df2 = df.toDF("id", "name", "age", "gender", "heigh")

    df.printSchema()
    df.show()

    println("----------------绿色的线--------------------")
    df2.printSchema()

    /**
     * 默认显示20行数据
     *  show(20)
     *  date
     */
    df2.show(false)

    session.close()

  }

}
