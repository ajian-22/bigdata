package p5_highlevelfunc

/**
 **
 * 柯里化是一个过程 , 一种形式
 * 原理的方法的参数列表只有一个 ,现在是两个
 * 两个参数列表的方法为柯里化的方法
 * 1 方便类型推导  和数据演变
 * 2 较少方法的参数传递
 *
 */
object C5_KeLiHua1 {

  //  给你一个集合  根据传入的逻辑寻找指定的元素
  // List(1,2,3,4,5,6,7)  大于3的元素 返回一个
  def findElement(ls:List[Int] , f:Int=>Boolean): Option[Int] ={
    ls match {
      case  Nil => None
      case head::tail=>if(f(head)) Some(head) else findElement(tail ,f)
    }
  }

  def main(args: Array[String]): Unit = {
    val opt: Option[Int] = findElement(List[Int](1, 2, 3, 4, 5, 6), _ > 3)
    opt match {
      case None => println("没有值")
      case Some(value) =>println(s"找到的元素是: $value")
    }

  }

}
