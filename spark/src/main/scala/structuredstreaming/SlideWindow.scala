package structuredstreaming


import org.apache.spark.sql.DataFrame
import org.apache.spark.sql.streaming.OutputMode
import utils.SparkUtil

case class wordTs(time: String, word: String)

object SlideWindow {
  def main(args: Array[String]): Unit = {



    val spark = SparkUtil.getSession
    spark.sparkContext.setLogLevel("warn")

    val df: DataFrame = spark.readStream
      .format("socket")
      .option("host", "localhost")
      .option("port", 9999)
      .load()

    import spark.implicits._
    val word: DataFrame = df.as[String].map(line =>{
      val wordAndTs = line.split(",")
      wordTs(wordAndTs(0),wordAndTs(1))
    }).toDF.selectExpr("cast(time as Timestamp) as ts","word")

    import org.apache.spark.sql.functions._
    val windowedCounts = word
      .withWatermark("ts","10 seconds")
      .groupBy(
      window($"ts", "10 minutes", "5 minutes"),
      $"word"
    ).count()
      //.sort("window")

    windowedCounts.writeStream
      .outputMode(OutputMode.Append())
      .format("console")
      .option("truncate","false")
      //.trigger(Trigger.Continuous("1 second"))
      .start()
      .awaitTermination()

    spark.close()
  }

}
