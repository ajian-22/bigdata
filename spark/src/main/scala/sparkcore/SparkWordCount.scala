package sparkcore

import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
 */
object SparkWordCount {

  def main(args: Array[String]): Unit = {

    val conf = new SparkConf()
    conf.setAppName(this.getClass.getSimpleName.stripSuffix("$")).setMaster("local[6]")
   // println(this.getClass.getSimpleName.stripSuffix("$"))
    //println(this.getClass.getSimpleName)

    // 构造一个用于运行spark程序的上下文对象
    val sc = new SparkContext(conf)
    // 加载文件获得RDD数据集
    val rdd1: RDD[String] = sc.textFile("file:///Users/duyaoyao/work/data/wc.txt", 2)

    // 对“集合[String]”中的每条数据应用函数： 按空格切分成数组，并压平，得到一个新的 "集合[String]"
    val rdd2: RDD[String] = rdd1.flatMap(s => s.split(" "))

    // 对 "集合[String]"中的每条数据应用函数：字符串=>元组，得到一个新的"集合[(String,Int)]"
    val rdd3 = rdd2.map(s => (s, 1))

    // 对"集合[(String,Int)]"进行分组聚合运算，分组key:单词, 聚合函数: 将1累加
    val rdd4 = rdd3.reduceByKey((c1, c2) => c1 + c2)

    // 将统计结果打印
    rdd4.foreach(println)

    sc.stop()

  }

}
