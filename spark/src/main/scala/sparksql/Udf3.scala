package sparksql

import utils.SparkUtil


/**
 *
 * 在Spark.functions中有大量的实现函数
 * 一般不需要自定义函数 , 特殊的需求需要自定义函数
 *
 *
 */
object Udf3 {
  def main(args: Array[String]): Unit = {
    def main(args: Array[String]): Unit = {
      val session = SparkUtil.getSession
      import session.implicits._

      val df = session.read
        .option("header", true)
        .option("inferSchema", true)
        .csv("spark/data/udf/users.txt")

      /**
       * 数据  / 结构
       * +---+----+---+------+------+------+-----+
       * | id|name|age|height|weight|yanzhi|score|
       * +---+----+---+------+------+------+-----+
       * |  1|   a| 18|   172|   120|    98| 68.8|
       * |  2|   b| 18|   172|   120|    98| 68.8|
       * |  3|   c| 30|   180|   130|    94| 88.8|
       * |  4|   d| 18|   168|   110|    98| 68.8|
       * |  5|   e| 26|   165|   120|    98| 68.8|
       * |  6|   f| 27|   182|   135|    95| 89.8|
       * |  7|   g| 19|   171|   122|    99| 68.8|
       * +---+----+---+------+------+------+-----+
       *
       * Process finished with exit code 0
       *
       * 组装数据
       */

      /**
       * 使用sql方式
       */
      df.createOrReplaceTempView("tb_user")

      val df1 = session.sql(
        """
          |
          |select
          |id ,
          |name ,
          |array(age , height , weight,yanzhi , score) as featrues
          |from
          |tb_user
          |
          |""".stripMargin)

      df1.printSchema()

      /**
       * DSL
       */

      // val df2 = df.select('id, 'name, array('age, 'height, 'weight, 'yanzhi, 'score).as("featrues"))


      /**
       * DF中的算子
       */

      /*   val df3 = df.map(row => {
           val id = row.getAs[Int]("id")
           val name = row.getAs[String]("name")
           val age = row.getAs[Int]("age")
           val height = row.getAs[Int]("height")
           val weight = row.getAs[Int]("weight")
           val yanzhi = row.getAs[Int]("yanzhi")
           val score = row.getAs[Double]("score")
           (id, name, Array[Double](age, height, weight, yanzhi, score))
         }).toDF("id", "name", "featrues")*/

      /**
       *
       * +---+----+--------------------+
       * | id|name|            featrues|
       * +---+----+--------------------+
       * |  1|   a|[18.0, 172.0, 120...|
       * |  2|   b|[18.0, 172.0, 120...|
       * |  3|   c|[30.0, 180.0, 130...|
       * |  4|   d|[18.0, 168.0, 110...|
       * |  5|   e|[26.0, 165.0, 120...|
       * |  6|   f|[27.0, 182.0, 135...|
       * |  7|   g|[19.0, 171.0, 122...|
       * +---+----+--------------------+
       */
      // 任意两个人的余弦相似度

      val joinedDF = df1.join(df1.toDF("id2", "name2", "featreus2"), 'id < 'id2)

      /**
       * +---+----+--------------------------------+---+-----+--------------------------------+
       * |id |name|featrues                        |id2|name2|featreus2                       |
       * +---+----+--------------------------------+---+-----+--------------------------------+
       * |1  |a   |[18.0, 172.0, 120.0, 98.0, 68.8]|2  |b    |[18.0, 172.0, 120.0, 98.0, 68.8]|
       * |1  |a   |[18.0, 172.0, 120.0, 98.0, 68.8]|3  |c    |[30.0, 180.0, 130.0, 94.0, 88.8]|
       * |1  |a   |[18.0, 172.0, 120.0, 98.0, 68.8]|4  |d    |[18.0, 168.0, 110.0, 98.0, 68.8]|
       * |1  |a   |[18.0, 172.0, 120.0, 98.0, 68.8]|5  |e    |[26.0, 165.0, 120.0, 98.0, 68.8]|
       * |1  |a   |[18.0, 172.0, 120.0, 98.0, 68.8]|6  |f    |[27.0, 182.0, 135.0, 95.0, 89.8]|
       * |1  |a   |[18.0, 172.0, 120.0, 98.0, 68.8]|7  |g    |[19.0, 171.0, 122.0, 99.0, 68.8]|
       * |2  |b   |[18.0, 172.0, 120.0, 98.0, 68.8]|3  |c    |[30.0, 180.0, 130.0, 94.0, 88.8]|
       * |2  |b   |[18.0, 172.0, 120.0, 98.0, 68.8]|4  |d    |[18.0, 168.0, 110.0, 98.0, 68.8]|
       * |2  |b   |[18.0, 172.0, 120.0, 98.0, 68.8]|5  |e    |[26.0, 165.0, 120.0, 98.0, 68.8]|
       * |2  |b   |[18.0, 172.0, 120.0, 98.0, 68.8]|6  |f    |[27.0, 182.0, 135.0, 95.0, 89.8]|
       * |2  |b   |[18.0, 172.0, 120.0, 98.0, 68.8]|7  |g    |[19.0, 171.0, 122.0, 99.0, 68.8]|
       * |3  |c   |[30.0, 180.0, 130.0, 94.0, 88.8]|4  |d    |[18.0, 168.0, 110.0, 98.0, 68.8]|
       * |3  |c   |[30.0, 180.0, 130.0, 94.0, 88.8]|5  |e    |[26.0, 165.0, 120.0, 98.0, 68.8]|
       * |3  |c   |[30.0, 180.0, 130.0, 94.0, 88.8]|6  |f    |[27.0, 182.0, 135.0, 95.0, 89.8]|
       * |3  |c   |[30.0, 180.0, 130.0, 94.0, 88.8]|7  |g    |[19.0, 171.0, 122.0, 99.0, 68.8]|
       * |4  |d   |[18.0, 168.0, 110.0, 98.0, 68.8]|5  |e    |[26.0, 165.0, 120.0, 98.0, 68.8]|
       * |4  |d   |[18.0, 168.0, 110.0, 98.0, 68.8]|6  |f    |[27.0, 182.0, 135.0, 95.0, 89.8]|
       * |4  |d   |[18.0, 168.0, 110.0, 98.0, 68.8]|7  |g    |[19.0, 171.0, 122.0, 99.0, 68.8]|
       * |5  |e   |[26.0, 165.0, 120.0, 98.0, 68.8]|6  |f    |[27.0, 182.0, 135.0, 95.0, 89.8]|
       * |5  |e   |[26.0, 165.0, 120.0, 98.0, 68.8]|7  |g    |[19.0, 171.0, 122.0, 99.0, 68.8]|
       * +---+----+--------------------------------+---+-----+--------------------------------+
       */


      //自定义余弦相似度函数

      val f = (arr1: Array[Double], arr2: Array[Double]) => {

        val fm1 = Math.pow(arr1.map(e => Math.pow(e, 2)).sum, 0.5)
        val fm2 = Math.pow(arr2.map(e => Math.pow(e, 2)).sum, 0.5)
        val tps = arr1.zip(arr2)
        val fz = tps.map(tp => {
          tp._1 * tp._2
        }).sum
        fz / (fm1 * fm2)
      }
      session.udf.register("cos_sime", f)
      joinedDF.createOrReplaceTempView("tb_join")
      session.sql(
        """
          |select
          |id ,
          |name  ,
          |id2 ,
          |name2 ,
          |cos_sime(featrues, featreus2)
          |from
          |tb_join
          |""".stripMargin).show()


    }


  }
}
