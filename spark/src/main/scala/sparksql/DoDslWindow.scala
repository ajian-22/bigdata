package sparksql

import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.expressions.Window
import org.apache.spark.sql.types.{DataTypes, StructType}

/**
 * * sparksql可以在sql(编写sql语句)
 * * 还可以在DataFrame上调用api的方式操作数据   DSL
 */
object DoDslWindow {

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    val session = SparkSession.builder()
      .master("local[*]")
      .appName("show")
      .enableHiveSupport()
      .getOrCreate()

    val stuSchema = new StructType()
      .add("id", DataTypes.IntegerType)
      .add("name", DataTypes.StringType)
      .add("gender", DataTypes.StringType)
      .add("age", DataTypes.IntegerType)



    val frame1 = session.read.schema(stuSchema).csv("spark/data/stu/student.txt")

    val window = Window.partitionBy("gender").orderBy("age")
    //frame1.select('id , 'name , 'age , sum("age").over(window))

    //  frame1.select('id , 'name ,'gender, 'age , row_number().over(window))
    //  frame1.select('id , 'name ,'gender, 'age , dense_rank().over(window))
    // .show()

  }


}
