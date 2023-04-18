package sparksql

import org.apache.log4j.{Level, Logger}
import org.apache.spark.sql.types.{DataTypes, StructType}
import org.apache.spark.sql.{Row, SparkSession}

/**
 * DataFrame  -->DataSet
 * 加载结构化数据  加载MySQL表  加载Hive --> DataFrame
 * hive表 -->DataFrame-->rdd-->算子-->rdd -->DataFrame--> hive
 * hive表 -->DataFrame-->DataSet[T]-->算子-->DataSet --> hive
 * DataFrame里面也有map方法  filter  groupBy  内部封装的数据只能是Row  [SQL风格] 灵活度不高
 * DataFrame的map方法  -->DataSet[T] map方法  filter  groupBy   处理的数据类型丰富灵活
 *
 *
 */
object DfDsRdd3 {

  Logger.getLogger("org").setLevel(Level.ERROR)
  def main(args: Array[String]): Unit = {
    System.setProperty("HADOOP_USER_NAME", "root")
    val session = SparkSession.builder()
      .master("local[*]")
      .appName("show")
      .enableHiveSupport()
      .getOrCreate()
    import session.implicits._

    val stuSchema = new StructType()
      .add("id", DataTypes.IntegerType)
      .add("name", DataTypes.StringType)
      .add("gender", DataTypes.StringType)
      .add("age", DataTypes.IntegerType)


    /**
     * DataFrame --> DataSet  Row-->T
     * DataSet-->DataFrame  ds.toDF()
     * DataFrame -- .rdd
     * DataSet  --  .rdd
     * RDD  .tdDS
     * RDD  .toDF(字段名)   不是所有的RDD 都能转
     */
    val frame1 = session.read.schema(stuSchema).csv("data/stu/student.txt")
    // DataFrame上的算子
    val ds = frame1.map({
      case Row(id: Int, name: String, gender: String, age: Int) => (id, name, age, gender)
    })

    //val frame: DataFrame = ds.toDF()

    // 混合更加灵活
    val rdd = ds.rdd

    val df = rdd.toDF("id", "name", "age", "gender")
    val ds2= rdd.toDS()


  }


}
