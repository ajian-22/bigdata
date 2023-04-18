package sparksql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.{DataTypes, StructType}

/**
 * * sparksql可以在sql(编写sql语句)
 * * 还可以在DataFrame上调用api的方式操作数据   DSL
 */
object DoDslJoin {

  Logger.getLogger("org").setLevel(Level.ERROR)

  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    val session = SparkSession.builder()
      .master("local[*]")
      .appName("show")
      //.enableHiveSupport()
      .getOrCreate()

    val stuSchema = new StructType()
      .add("id", DataTypes.IntegerType)
      .add("name", DataTypes.StringType)
      .add("age", DataTypes.IntegerType)

    val subjectSchema = new StructType()
      .add("subject", DataTypes.StringType)
      .add("score", DataTypes.DoubleType)
      .add("sid", DataTypes.IntegerType)

    val frame1 = session.read.schema(stuSchema).csv("spark/data/stu/student.txt")
    val frame2 = session.read.schema(subjectSchema).csv("spark/data/stu/subject.txt")
    /**
     *  t1 join t2  using (id)   两个表的关联字段名字一致
     *  t1 join t2 on  t1.id = t2.id
     */
    // val resDF = frame1.join(frame2, "id")
    /**
     * 参数二  关联条件
     * 参数三   关联类型  left    right  full
     */
    val resDF = frame1.join(frame2, frame1.col("id")  ===  frame2.col("sid") , "left")

    resDF.printSchema()
    resDF.show()


  }

}
