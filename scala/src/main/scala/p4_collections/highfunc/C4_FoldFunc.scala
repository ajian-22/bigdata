package collections.highfunc

/**
 * 折叠是归约操作类似于reduce函数  ,但是fold函数中多出来一个初始值
 */
object C4_FoldFunc {

  def main(args: Array[String]): Unit = {
    val arr = Array("tom", "cat", "jim", "rose")
    // 遍历集合中的每个元素进行拼接  比reduce函数多出一个初始值
    val res = arr.fold("hello")(_ + " " + _)
    println(res)
    val ls = List(1, 3, 5, 7)
    // 100+1)+3)+5)+7 底层调用的是  foldLeft
    val res2 = ls.fold(100)(_ + _) // 116
    ls.foldLeft(100)(_ + _) //  116

    //从右边开始运算 默认的值先参与运算进来
    // 7-10)-->5-(-3)-->3-8 -->1-(-5)
    val res01: Int = ls.foldRight(10)(_ - _) //6

  }
}
