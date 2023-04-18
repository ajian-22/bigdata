package sparksql

import org.apache.spark.rdd.RDD
import utils.SparkUtil


object DoSql {
  def main(args: Array[String]): Unit = {
    val session = SparkUtil.getSession

    /**
     * 文本文件  不是一个结构化的内容
     */

    val sc = session.sparkContext
    val rdd = sc.textFile("spark/data/word/")
    val words: RDD[String] = rdd.flatMap(_.split("\\s+"))

    /**
     * spark  sql
     * RDD --> DF
     */
    import session.implicits._
    val df = words.toDF("word")

    df.createOrReplaceTempView("words")

    session.sql(
      """
        |select
        |word ,
        |count(1)  as cnt
        |from
        |words
        |group by word
        |""".stripMargin).show()

    // df.groupBy("word").count().show()


  }

}
