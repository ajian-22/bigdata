package p5_highlevelfunc

/**
 * 柯里化是一个过程 , 一种形式
 * 原理的方法的参数列表只有一个 ,现在是两个
 * 两个参数列表的方法为柯里化的方法
 * 1 方便类型推导  和数据演变
 * 2 较少方法的参数传递
 */
object C5_KeLiHua2 {

  /**
   * findElement(ls:List[Int] , f:Int=>Boolean): Option[Int]
   * findElement(ls:List[Int])()
   * 这样的方法 传入的数据类型是固定的 只能传入Int类型的集合
   * 想传入任意的数据类型:  使用泛型  T代表任意类型
   *
   * @param ls
   * @param f
   * @tparam T
   * @return
   */
  def findElement[T](ls: List[T], f: T => Boolean): Option[T] = {
    ls match {
      case Nil => None
      case head :: tail => if (f(head)) Some(head) else findElement(tail, f)
    }
  }

  def findElement2[T](ls: List[T])(f: T => Boolean): Option[T] = {
    ls match {
      case Nil => None
      case head :: tail => if (f(head)) Some(head) else findElement2(tail)(f)
    }
  }

  def main(args: Array[String]): Unit = {
    val opt: Option[Int] = findElement(List[Int](1, 2, 3, 4, 5, 6), (e: Int) => e > 2)
    // e 不会根据前面的List[Int] 内容推算自己的数据类型是Int 所以不能  >
    // val opt: Option[Int] = findElement(List[Int](1, 2, 3, 4, 5, 6), (e)=>e>2)
    //
    val opt2 = findElement2(List[Int](1, 2, 3, 4, 5, 6))((e) => e > 2)

    opt2 match {
      case None => println("没有值")
      case Some(value) => println(s"找到的元素是: $value")
    }

  }


}
