package collections.highfunc

import p2_object.User


object C4_DistinctFunc {

  def main(args: Array[String]): Unit = {
    // 样例类可以比较对象的内容 重写了HashCode和equals方法
    val ls = List(User(2, "zss", 23), User(2, "zss", 23), User(3, "ww", 34))
    val res: List[User] = ls.distinct
    println(res)
    //   ls.distinctBy(user=>user.age)
    //res.foreach(println)


  }

}
