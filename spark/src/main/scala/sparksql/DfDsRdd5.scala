package sparksql

import utils.SparkUtil


object DfDsRdd5 {

  def main(args: Array[String]): Unit = {
    val session = SparkUtil.getSession
    val sc = session.sparkContext
    val  ls = List[Int](1,2,3,4)
    val rdd = sc.parallelize(ls)

    import session.implicits._
    val frame = rdd.toDF("member")

    val  mp = Map[String,Int](("zss",23),("lss",33)).toSeq

    val mpRDD = sc.makeRDD(mp)
    val frame1 = mpRDD.toDF("k" , "v")
    frame1.printSchema()
    frame1.show()


    frame.show()


  }


}
