package p5_highlevelfunc

import java.util.Random

import p2_object.{Student2, Student3, User}

/**
 * 模式匹配  :判断某种类型 , 值 , 结构是否满足要求
 *
 */
object C2_CaseMatch {

  def main(args: Array[String]): Unit = {
    val arr = Array[String]("lny", "wdl", "pjl", "lyf", "wyf")
    val random = new Random()
    val index: Int = random.nextInt(arr.length)
    val name: String = arr(index)
    println(name)
    // 值匹配
    name match {
      case "lny" => println("刘老师还很嫩...")
      case "wdl" => println("武老板卖脆饼...")
      case "pjl" => println("这娘们不是好人...")
      case "lyf" => println("罗永浩的亲妹妹...")
      case _ => println("啥也不是...")
    }
    println("####################################")
    val ls = List("lny", 1, 23.23d, true, Student2(1, "zss", 12), new Student3(1, "haha"))
    val random2 = new Random()
    val index2: Int = random2.nextInt(ls.length)
    val e = ls(index2)
    println(e)
    e match {
      case x: Int => println(s"这是一个Int类型的数据,值是: $x")
      case x: String => println(s"这是一个String类型的数据,值是: $x")
      case x: Boolean => println(s"这是一个布尔类型的数据,值是: $x")
      case x: Student2 => println(s"这是一个Student2类型的数据,值是: ${x.name}")
      case x: Student3 => println(s"这是一个Student3类型的数据,值是: ${x.name}")
      case _ => println("是个Double类型  ")
    }
    println("####################################")
    val ls2 = List("lny", 1, 23.23d, true, User(1, "zss", 12))
    val res2 = ls2.map(e => {
      e match {
        case x: String => s"HELLO$x"
        case x: Double => x * 10
        case x: Boolean => false
        case e: User => println(e)
        case _ =>
      }
    })
    res2.foreach(println)
  }

}
