package sparkcore

import utils.SparkUtil


object CheckPoint {

  def main(args: Array[String]): Unit = {
    val sc = SparkUtil.getSc
    val rdd1 = sc.textFile("data/a.txt")

    val rdd2 = rdd1.flatMap(line => {
      println("切割每行数据........")
      line.split("\\s+")
    })

    val rdd3 = rdd2.map(word => {
      println("将每个单词大写.............")
      word.toUpperCase()
    })

    val rdd4 = rdd3.map(word => {
      println("组装成单词和1")
      (word, 1)
    })
    // 将RDD4 缓存 . 能否重复使用
    // RDD4的中间结果可以被后面的计算重复的使用   避免的大量的重复计算工作
    // rdd4.cache()


    // 设置checkpoint的路径
    sc.setCheckpointDir("data/ck")
    // 将所有的RDD的血源关系断开
    rdd4.checkpoint()
    rdd4.count()
    /**
     * 同时使用ck 和cache时，先进行checkpoint，再cache,  结果时依赖关系会从checkpointRDD开始，是不是表明，checkpoint会让cache无效。
     */
    val rdd5 = rdd4.map(e => e)
    val rdd6 = rdd4.map(e => e)

    rdd5.count()
    rdd5.count()

    Thread.sleep(2000000)

  }
}
