package sparksql

import org.apache.log4j.{Level, Logger}
import utils.SparkUtil

import scala.beans.BeanProperty

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
object DfDsRdd4 {

  Logger.getLogger("org").setLevel(Level.ERROR)
  def main(args: Array[String]): Unit = {
    val session = SparkUtil.getSession
    import session.implicits._

    /**
     * 文本文件  不是一个结构化的内容
     */

    val sc = session.sparkContext
    val rdd = sc.textFile("data/rdd/")
    /*=====================元组类型=============================*/
    val tupleRDD = rdd.map(line => {
      /**
       * 4,刘备,26,上海,83.0
       * 5,曹操,30,深圳,90.0
       */
      val arr = line.split(",")
      (arr(0).toInt, arr(1), arr(2).toInt, arr(3), arr(4).toDouble)
    })
    /**
     * 转换成DF
     */
    val df = tupleRDD.toDF("id", "name", "age", "city", "score")

    // session.createDataFrame(tupleRDD).show()

    /*=====================样例类类型=============================*/
    val caseClassRDD = rdd.map(line => {
      /**
       * 4,刘备,26,上海,83.0
       * 5,曹操,30,深圳,90.0
       */
      val arr = line.split(",")
      Account(arr(0).toInt, arr(1), arr(2).toInt, arr(3), arr(4).toDouble)
    })
    val df2 = caseClassRDD.toDF()

    // session.createDataFrame(caseClassRDD).show()

    /*=====================普通scala类型=============================*/
    /**
     * 不能直接将 普通的scalaBean转换成DF
     * 没有  toDF方法
     */
    val scalaClassRDD = rdd.map(line => {
      /**
       * 4,刘备,26,上海,83.0
       * 5,曹操,30,深圳,90.0
       */
      val arr = line.split(",")
      new  Account2(arr(0).toInt, arr(1), arr(2).toInt, arr(3), arr(4).toDouble)
    })
    /**
     * 将RDD转换成DataFrema   将RDD中存储的数据结构 转成  Row结构
     * 普通的scala类不能直接解析自己的结构  ,但是可以通过反射解析 , 底层调用getXX方法
     * 反射解析 scala类的结构  封装到Row
     */
    val df3 = session.createDataFrame(scalaClassRDD, classOf[Account2])


    /*=====================普通JavaBean类型=============================*/

    val javaClassRDD = rdd.map(line => {
      /**
       * 4,刘备,26,上海,83.0
       * 5,曹操,30,深圳,90.0
       */
      val arr = line.split(",")
      //new AccountJavaBean(arr(0).toInt, arr(1), arr(2).toInt, arr(3), arr(4).toDouble)
    })

    /**
     * 底层调用java类的Get方法解析类属性
     * 将类数据 封装成Row 变成DF
     */
    //session.createDataFrame(javaClassRDD , classOf[AccountJavaBean]).show()

  }
}


case  class Account(id:Int , name:String , age:Int , city:String , score:Double)


class Account2(
                @BeanProperty
                val id:Int ,
                @BeanProperty
                val name:String ,
                @BeanProperty
                val age:Int ,
                @BeanProperty
                val city:String ,
                @BeanProperty
                val score:Double)
