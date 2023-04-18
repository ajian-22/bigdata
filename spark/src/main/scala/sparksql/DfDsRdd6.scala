package sparksql

import org.apache.spark.rdd.RDD
import org.apache.spark.sql.{DataFrame, Dataset, Row}
import utils.SparkUtil

case class Student(id: Int, name: String, sex: String, age: Int)

object DfDsRdd6 {

  def main(args: Array[String]): Unit = {

    val spark = SparkUtil.getSession
    val sc = spark.sparkContext

    import spark.implicits._

    val rdd1: RDD[Student] = sc.textFile("spark/data/stu/student.txt")
      .map(line => {
        val strs = line.split(",")
        Student(strs(0).toInt,strs(1),strs(2),strs(3).toInt)
      })


    val ds: Dataset[Student] = spark.createDataset(rdd1)
    val ds2: Dataset[Student] = rdd1.toDS


    ds2.printSchema()
    ds2.show(10,false)

    val df: DataFrame = ds.selectExpr("id * 10 as id" ,"upper(name) as name","sex","age","age+10 as tenAge")
    df.show(10)


    df.rdd.map({
      case Row(id:Int,name :String,sex:String,age:Int,tenAge:Int) =>(id,name,sex,tenAge)
    }).take(2).foreach(println)

    spark.close()

  }

}
