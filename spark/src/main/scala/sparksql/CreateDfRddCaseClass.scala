package sparksql

import org.apache.spark.rdd.RDD
import utils.SparkUtil

case  class  Teacher(tid:Int , tName:String, tage:Int, tgender:String , tHeight:Long)
object CreateDfRddCaseClass {

  def main(args: Array[String]): Unit = {
    val session = SparkUtil.getSession
    // 获取RDD
    val sc = session.sparkContext

    val rdd: RDD[String] = sc.textFile("spark/data/teacher/")
    // 加载数据  封装成RDD
    val caseClassRDD: RDD[Teacher] = rdd.map(line => {
      val arr = line.split(",")
      //2,星哥,46,M,180
      Teacher(arr(0).toInt, arr(1), arr(2).toInt, arr(3), arr(4).toLong)
    })
    /**
     * case class 有数据结构 属性名  属性数据类型
     */
    val df = session.createDataFrame(caseClassRDD)

    df.printSchema()
    df.show()

    session.close()

  }

}
