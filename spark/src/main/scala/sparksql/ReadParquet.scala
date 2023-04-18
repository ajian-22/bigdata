package sparksql

import utils.SparkUtil


object ReadParquet {
  def main(args: Array[String]): Unit = {
    val spark = SparkUtil.getSession
    //parquet
    val df = spark.read.parquet("spark/data/parquet/")
    /**
     * root
     * |-- uid: long (nullable = true)
     * |-- username: string (nullable = true)
     * |-- age: integer (nullable = true)
     * |-- sex: string (nullable = true)
     *
     * +---+--------+---+---+
     * |uid|username|age|sex|
     * +---+--------+---+---+
     * |  1|     zss| 23|  M|
     * |  2|     lss| 33|  M|
     * |  3|      ww| 21|  F|
     * +---+--------+---+---+
     *
     *
     * Process finished with exit code 0
     */

    df.printSchema()
    df.show()
    spark.close()

  }

}
