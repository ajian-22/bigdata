package collections.highfunc

/**
* foreach 函数  遍历任意集合中的每个元素进行处理  没有返回值  常用于打印和输出
*/
object C1_ForeachFunc {

  def main(args: Array[String]): Unit = {
    val mp = Map("zss"->23 , "lss"->33)
    val list = mp.toList
    list.foreach(println)
    //println(list)
    mp.foreach(tp=>println((tp._1.toUpperCase() , tp._2+5)))
  }
}
