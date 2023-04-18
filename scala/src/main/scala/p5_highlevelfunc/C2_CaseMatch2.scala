package p5_highlevelfunc

import p2_object.{User, User2}

/**
 * 模式匹配  :判断某种类型 , 值 , 结构是否满足要求
 * 匹配数据的内部结构
 */
object C2_CaseMatch2 {

  def main(args: Array[String]): Unit = {
    val ls = List[Int](5, 3, 4, 6)


    ls match {
      case List(3, x, _*) => println(s"这是一个以3为第一个元素的list集合: $x")
      case List(5, _, _, _) => println(s"这是一个以5为第一个元素的list集合:")
      case List(4, x, y) => println(s"这是一个以3为第一个元素的list集合: $x $y")
      case List(_, _, _, _) => println(s"这是一个4个元素的list集合:")
      case _ => println("那不知道")
    }
    println("########################")
    val tp = (1, 2, 3)
    tp match {
      case (x: Int, 2, z: Int) => println("这是一个3个元素元组")
      case (x: Int, 3, z: Int) => println("这是一个4个元素元组")
      case (x: Int, y: Int, 4) => println("这是一个5个元素元组")
      case _ => println("其他")
    }

    println("########################")
    // 样例类支持匹配模式
    val user: User = User(1, "zss", 23)

    val user2: User2 = User2(1, "zss", 23,98)

    user2 match {
      case e:User2 => println(s"${e.id}")
    }

  }

}
