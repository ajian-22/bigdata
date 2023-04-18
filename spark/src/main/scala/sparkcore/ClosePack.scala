package sparkcore

import utils.SparkUtil


object ClosePack{

  def main(args: Array[String]): Unit = {
    val sc = SparkUtil.getSc
    // 创建RDD
    val rdd = sc.makeRDD(Array(1, 2, 3, 4, 5, 6))
    // 创建引用类型的变量                    在Driver端创建[***]
    val user = new User(1, "zss", 23)
    // 调用转换算子   map(func)             func在executor端执行[###]

    val rdd2 = rdd.map(e => {
      // 使用算子[外边]的一个引用类型的变量的属性
      user.age = user.age + 3
    })

    // func执行  必须调用行动算子 触发执行
    //  Task not serializable
    rdd2.foreach(println)
    println(user.age)



  }

}

class User(val id:Int , val name:String , var age:Int)  extends  Serializable {
}
