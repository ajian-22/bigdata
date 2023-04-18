package utils

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.{SparkConf, SparkContext}


object SparkUtil {

  // 设置日志级别
  Logger.getLogger("org").setLevel(Level.WARN)
  /**
   * 获取Spark编程环境对象
   * @return
   */
  def getSc:SparkContext = {
    // 本地运行模式  local[1]
    //val conf = new SparkConf().setMaster("local").setAppName("随便啦...")
    //val conf = new SparkConf().setMaster("local[1]").setAppName("随便啦...")  // 使用当前机器的一个核
    // val conf = new SparkConf().setMaster("local[8]").setAppName("随便啦...")  // 使用当前你机器的8个核
    val conf = new SparkConf().setMaster("local[*]").setAppName("随便啦...")  // 使用当前你机器所有的核 // 16   1   4

    new SparkContext(conf)
  }

  def  getSession:SparkSession={
    SparkSession
      .builder()
      .master("local[*]")
      .appName("获取 spark-sql环境")
      .getOrCreate()
  }

}
