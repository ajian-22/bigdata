package p5_highlevelfunc

/**
 * 柯里化是一个过程 , 一种形式
 * 原理的方法的参数列表只有一个 ,现在是两个
 * 两个参数列表的方法为柯里化的方法
 * 1 方便类型推导  和数据演变
 * 2 减少方法的参数传递
 * 3 将数据和处理逻辑分离
 */
object C5_KeLiHua4 {

  def opt(x: Int, y: Int)(f: (Int, Int) => Int): Int = {
    f(x, y)
  }

  def main(args: Array[String]): Unit = {
    val res = opt(2, 4)(_ + _)
    println(res)
    //x(_+_)
  }

}
