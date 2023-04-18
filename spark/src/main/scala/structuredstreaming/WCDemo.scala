package structuredstreaming

import org.apache.spark.sql.streaming.{OutputMode, Trigger}
import org.apache.spark.sql.{DataFrame, Dataset}
import utils.SparkUtil

object WCDemo {
  def main(args: Array[String]): Unit = {

    val spark = SparkUtil.getSession
    spark.sparkContext.setLogLevel("warn")

    val df: DataFrame = spark.readStream
      .format("socket")
      .option("host","localhost")
      .option("port", 9999)
      .load()

    import spark.implicits._
    val word: Dataset[String] = df.as[String].flatMap(_.split("\\s+"))

    val wc: DataFrame = word.groupBy("value").count()

    wc.writeStream
      .outputMode(OutputMode.Update())
      .format("console")
      .trigger(Trigger.Continuous(""))
      .start()
      .awaitTermination()

    spark.close()
  }

}
