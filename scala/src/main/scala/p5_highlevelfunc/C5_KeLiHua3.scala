package p5_highlevelfunc

/**
 * 柯里化是一个过程 , 一种形式
 * 原理的方法的参数列表只有一个 ,现在是两个
 * 两个参数列表的方法为柯里化的方法
 * 1 方便类型推导  和数据演变
 * 2 减少方法的参数传递
 */
object C5_KeLiHua3 {

  def mkSal1(deptno: Int, sal: Int, comm: Int): Int = {
    sal+comm
  }

  def mkSal(deptno: Int, sal: Int)(comm: Int): Int = {
    sal + comm
  }

  def main(args: Array[String]): Unit = {
    val x = mkSal(10, 20000)(2000)
    println(x)
    val f: Int => Int = mkSal(10, 20000)
    val zss: Int = f(1000)
    val lss: Int = f(2000)
    val ww: Int = f(3000)
    val zl: Int = f(4000)
    println(zl)
    println(zss)
    println(zl)
    println(zl)

    println(mkSal1(10, 20000, 2000))
    //mkSal1(10, 20000, 2000)
    //mkSal1(10, 20000, 3000)
  }

}
