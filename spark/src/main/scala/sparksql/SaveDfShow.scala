package sparksql

import org.apache.spark.sql.SparkSession

object SaveDfShow {

  def main(args: Array[String]): Unit = {
    val session = SparkSession.builder()
      .master("local[*]")
      .appName("show")
      .getOrCreate()
    val  df = session.read.option("header" , true).option("inferSchema",true).csv("spark/data/user/user2.csv")
    df.show()
    df.show(2)
    df.show(false)
    df.show(2 ,true)
    session.close()

  }

}
