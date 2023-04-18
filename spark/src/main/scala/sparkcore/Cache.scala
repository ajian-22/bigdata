package sparkcore

import org.apache.spark.storage.StorageLevel
import utils.SparkUtil


object Cache {

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

    // rdd4.persist()
    rdd4.persist(StorageLevel.MEMORY_AND_DISK_2)

    // 每个单词的总次数
    val res1 = rdd4.join(rdd4)
    res1.collect()

    Thread.sleep(200000)


  }

}
