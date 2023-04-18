package p5_highlevelfunc

import p2_object.User


object C3_CaseMatch4 {

  def main(args: Array[String]): Unit = {
    var x: Any = 12f

    // 值匹配
    x match {
      case 12 => println("匹配到数值  12")
      case 2 => println("匹配到数值  2")
      case 3 => println("匹配到数值  3")
      case "hello" => println("你好")
      case _ => println("啥也不是..")
    }

    x = 12.3d
    x = Array(1, 2, 3, 4)
    //  x=new MyUser
    // 类型匹配  注意类型匹配 加变量 x: 类型
    // 类型匹配可以匹配任意的数据类型  包括自定义的类
    x match {
      case e: String => println(s"String类型的数据 $e")
      case e: Int => println(s"Int数据类型 ")
      case e: Double => println(s"Double数据类型 ")
      // 可以匹配集合类型和数据类型
      case e: Array[Int] => println("这是一个数组")
      case e: List[String] => println("List集合")
      case e: Map[String, Int] => println("map集合")
      //case e: MyUser => println("MyUser")
      case _ => println("不知道什么类型")
    }

    x = List(1, 2, 3)
    x = Array("a", "b")
    x = Map(("zss" -> 23, "lss" -> 23))
    x = User(1, "zss", 19)
    // x = 11::22::33::44::Nil
    // 数据的内部结构  解析结构
    x match {
      case List(1, x, y, z) => println(s"$x $y $z")
      case List(_, _, x, _, _, _) => println(s"5个元素  第三个位置是 $x")
      case head :: Nil => println(s"只有一个元素的list集合  第一个元素是 $head")
      case head :: second :: thr :: Nil => println(s"3个元素的list集合  第一个元素是 $head")
      case List(x, y, z) => println(s"3个元素的list集合  第一个元素是 $x")
      case Array(x, _, y) => println("两个元素的数组")

    }


  }

}
