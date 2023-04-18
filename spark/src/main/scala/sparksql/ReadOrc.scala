package sparksql

import utils.SparkUtil


object ReadOrc {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSession
    //加载ORC文件
    //val df = spark.read.orc("spark/data/orc/")

    val df = spark.read.format("orc").load("spark/data/orc/")


    /**
     * root
     * |-- uid: long (nullable = true)
     * |-- username: string (nullable = true)
     * |-- age: integer (nullable = true)
     * |-- gender: string (nullable = true)
     *
     * +---+--------+---+------+
     * |uid|username|age|gender|
     * +---+--------+---+------+
     * |  1|     zss| 23|     M|
     * |  2|     lss| 33|     M|
     * |  3|      ww| 21|     F|
     * +---+--------+---+------+
     */

    df.printSchema()
    df.show()
    spark.close()

  }

}
