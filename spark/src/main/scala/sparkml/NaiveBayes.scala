package sparkml

import com.hankcs.hanlp.HanLP
import org.apache.log4j.{Level, Logger}
import org.apache.spark.ml.classification.{NaiveBayes, NaiveBayesModel}
import org.apache.spark.ml.feature.{HashingTF, IDF}
import org.apache.spark.sql.{DataFrame, SparkSession}



object NaiveBayes {
  def main(args: Array[String]): Unit = {
    Logger.getLogger("org").setLevel(Level.WARN)
    //创建spark入口 1000条数据集  80%
    val spark = SparkSession.builder()
      .master("local[3]")
      .appName(this.getClass.getSimpleName.stripSuffix("$"))
      .getOrCreate()

    //读取数据组装df
    val df: DataFrame = spark.read
      .option("header", "true")
      .option("delimiter", " ")
      .option("inferSchema", "true")
      .csv("spark/data/sparkml/apple.csv")

    //df.show(3, false)

    import scala.collection.JavaConversions._
    val f = (line: String) => HanLP.segment(line).map(term => term.word).toArray
    spark.udf.register("toWords", f)
    val df2 = df.selectExpr("*", "toWords(content) as words")

    df2.show(5, false)

    //转化tf
    val hashTF = new HashingTF()
      .setInputCol("words")
      .setOutputCol("tf")
      .setNumFeatures(100000)

    val tf: DataFrame = hashTF.transform(df2)

    //转化idf
    val idf = new IDF()
      .setInputCol("tf")
      .setOutputCol("idf")

    val idfDF = idf.fit(tf)
      .transform(tf)
      .drop("tf")

    //idfDF.show(10,false)

    //训练模型
    val bayes = new NaiveBayes().setFeaturesCol("label").setSmoothing(0.01).setFeaturesCol("idf")
    val model = bayes.fit(idfDF)

    //模型评估...  剩余的20%样本去校验模型
    //new BinaryClassificationMetrics()

    //保存模型
    model.save("spark/data/sparkml/output")

    //使用模型
    val model1 = NaiveBayesModel.load("output")
    model1.transform(df).show()

    spark.close()
  }


}
