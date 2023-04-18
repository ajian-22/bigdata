package org.loess.com

import org.apache.flink.api.scala.ExecutionEnvironment

object WordCountBatch_Scala {

  def main(args: Array[String]): Unit = {

    //1.flink环境
    val env = ExecutionEnvironment.getExecutionEnvironment

    //2.创建source
    val source = env.readTextFile("G:\\BIGDATA\\Spark\\projects\\bigdata-master\\flink\\data\\wc\\wordcount.txt")

    //3.根据业务 对数据进行逻辑处理  transform
    import org.apache.flink.api.scala._
    source.flatMap(_.split(" "))
      .map((_, 1))
      .groupBy(0)
      .sum(1)
      //4.sink 输出
      .print()

    //批处理没有这一行
    //    env.execute("WordCountBatch_Scala")
  }
}
