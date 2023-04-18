package collections.highfunc

/**
 * scan(默认值)(计算逻辑)   显示计算过程
 * 方法的参数中 有一个参数是函数   高阶函数
 * 将处理的数据放在一个参数列表中  函数放在另一个参数列表中
 * 原来的方法一个参数列表  一个方法两个参数列表  柯里化的方法
 * def opt(x:Int , y:Int)(f:(Int,Int)=>Int): Int ={
 * f(x,y)
 * }
 */
object C4_ScanFunc {

  def main(args: Array[String]): Unit = {
    // println(opt(3, 4)((x, y) => x * y))
    val ls = List(1, 2, 3, 4, 5, 6)
    //(0)+1)+2)+3)+4)+5)+6  柯里化的方法
    // 参数列表1  默认值 参与一次运算
    val res: List[Int] = ls.scan(10)(_ + _)
    res.foreach(println)
    val arr = Array[String]("java", "sql", "scala", "hbase")
    val res2: Array[String] = arr.scan("hello")(_ + "-" + _)
    res2.foreach(println)
  }

}