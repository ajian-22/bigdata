package sparkgraphx

import org.apache.commons.lang3.StringUtils
import org.apache.spark.graphx.{Edge, Graph, VertexId}
import org.apache.spark.rdd.RDD
import org.apache.spark.sql.SparkSession


object GraphxDemo {

  def main(args: Array[String]): Unit = {
    //1、创建sparksession
    val spark = SparkSession.builder()
      .appName(this.getClass.getSimpleName.stripSuffix("$"))
      .master("local[2]")
      .getOrCreate()

    spark.sparkContext.setLogLevel("warn")

    //2、读取数据
    val df = spark.read
      .option("header", "true")
      .csv("spark/data/graphx/user.csv")

    //df.show()
    df.cache()
    //3、构建点vertex(long,any)
    val vertexs: RDD[(Long, String)] = df.rdd.flatMap(row => {
      val phone = row.getString(0)
      val name = row.getString(1)
      val wechat = row.getString(2)

      Array(phone, name, wechat)
        .filter(StringUtils.isNotBlank(_))
        .map(id => (id.hashCode.toLong, id))
    })

    //vertexs.take(20).foreach(println)
    /**
     * (208397334,13866778899)
     * (20977295,刘德华)
     * (113568560,wx_hz)
     * (-1485777898,13877669988)
     * (681286,华仔)
     * (113568560,wx_hz)
     * (20977295,刘德华)
     */

    //4、描述点和点之间的边edge
    val edges: RDD[Edge[String]] = df.rdd.flatMap(row => {
      val phone = row.getString(0)
      val name = row.getString(1)
      val wechat = row.getString(2)

      val ids = Array(phone, name, wechat).filter(StringUtils.isNotBlank(_))
      for (i <- 1 until ids.length) yield Edge(ids(0).hashCode.toLong, ids(i).hashCode.toLong, "")
    })

    //5、构建图
    val graph = Graph(vertexs, edges)

    //6、调用图上的方法
    val value = graph.connectedComponents().vertices
    value.take(20).foreach(println)

    //7、进行join 解析算法结果，成为我们所需要的形式 （）
    val result: RDD[(VertexId, List[String])] = value
      .join(vertexs)
      .map(tp => tp._2)
      .groupByKey()
      .map(tp => (tp._1, tp._2.toList))
    result.take(20).foreach(println)
    import spark.implicits._
    /**
     * (-1095633001,List(13912344321, 13912344321, 猪八戒, wx_bj, wx_bj, 13912664321, wx_mdh, 二师兄, 马德华))
     * (-1485777898,List(wx_hz, wx_hz, 13877669988, 华仔, 刘德华, 刘德华, wx_ldh, 13866778899))
     */
    result.map(t => (t._1.toLong,t._2)).toDF("hashid","marklist").write.mode("overwrite").parquet("spark/data/graphx/out")
    //释放资源
    spark.close()
  }

}
