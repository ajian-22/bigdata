package collections.highfunc

import p2_object.Student2

/**
 * 对于简单的获取最大 最小 可以使用maxBy  minBy  方法指定维度
 * 原来 重写排序  固定的逻辑
 * by指定排序的字段  字段一般都是课排序
 * By 通过
 */


object C4_MinMaxByFunc {

  def main(args: Array[String]): Unit = {

    val stus = List[Student2](
      Student2(3, "zss", 23),
      Student2(2, "lss", 45),
      Student2(1, "lny", 34)
    )
    println(stus.maxBy(_.age))
    println(stus.minBy(_.id))
  }
}
