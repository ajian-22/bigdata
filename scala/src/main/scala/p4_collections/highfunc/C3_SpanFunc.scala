package collections.highfunc

object C3_SpanFunc {

  def main(args: Array[String]): Unit = {
    val list = List(5, 7, 1, 2, 3, 4, 5, 6, 7, 8, 9, 9, 9)
    //从第一个元素开始判断是否符合要求 , 如果符合要求返回到第一组 ,如果遇到不符合要求的元素 , 判断终止 , 后面所有的元素放在第二组
    val tuple: (List[Int], List[Int]) = list.span(_ > 3)
    println(tuple._1)


  }
}
