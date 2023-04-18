package sparkcore

import utils.SparkUtil


object PartitionChange {

  def main(args: Array[String]): Unit = {
    val sc = SparkUtil.getSc
    val rdd = sc.makeRDD(List(1, 2, 3, 4, 5), 2)
    // 修改前的分区数
    println(rdd.getNumPartitions)
    /**
     * 参数一  新的RDD的分区数
     * 参数二  是否允许shuffle操作    默认是false  不能增加分区的个数
     * true  允许shuffle  分区可以增加
     */
    //  val rdd2  = rdd.coalesce(3 , true) // 增加分区个数  产生shuffle
    val rdd2 = rdd.coalesce(1)
    // 增加分区个数  产生shuffle
    // 修改后的分区个数
    println(rdd2.getNumPartitions) // 没有修改成功
    // 增加分区的个数
    val res = rdd.repartition(3)
    println(res.getNumPartitions)
    // 减少分区的个数
    val res2 = rdd.repartition(1)
    println(res2.getNumPartitions)

    rdd.foreachPartition(part => {
      //创建MySQL的连接con
      part.foreach(line => {
        //(,,,,)
        //insert
      })
    })

  }

}
