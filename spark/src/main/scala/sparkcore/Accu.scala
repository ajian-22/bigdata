package sparkcore

import utils.SparkUtil


object Accu {

  def main(args: Array[String]): Unit = {
    val sc = SparkUtil.getSc
    // 创建RDD
    val rdd = sc.makeRDD(Array(1, 2, 3, 4, 5, 6) , 2)
    /**
     * 系统中有 ==全局累加器==
     *   在各个excutor端计算 统计
     *   将结果返回到Driver端  汇总统计
     *   [excutor01: 1 2 3] 1+1+1 = 3
     *   [excutor04: 4 5 6]  1+1+1 = 3
     *   3+3 = 6===?结果
     */

    //  1 创建累加器实例
    val myCnt = sc.longAccumulator("myCnt")

    // 统计RDD中处理所有数据的个数
    var cnt = 0
    rdd.foreach(e => {
      // 操作的是Driver端变量的副本
      cnt += 1
      // 2 调用累加器
      myCnt.add(1)
    })

    // 注意  累加器在算子中执行   获取结果 一定是在行动算子触发之后
    // 累加器最好写在行动算子中
    // 3 打印结果  获取累加器的值
    val cntNumber = myCnt.value
    println("=================累加器计算的结果是============:"+cntNumber)

    // 本身数据不会变化的
    //println(cnt)



    /* val cnt = rdd.count()
      println(cnt)*/

  }

}
