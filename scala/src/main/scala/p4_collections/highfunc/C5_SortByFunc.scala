package p4_collections.highfunc

import p2_object.{Student, Student2}

/**
 * sortBy  对弈自定义的元元素 ,一些简单的单维度排序可以使用 sortBy
 */
object C5_SortByFunc {

  def main(args: Array[String]): Unit = {

    val ls1 = List[Student](
      Student(3, "zss", 23),
      Student(2, "lss", 45),
      Student(1, "lny", 34)
    )
    // 没有排序规则   不能sorted直接排序
    val ls2 = List[Student2](
      Student2(3, "azss", 23),
      Student2(2, "xlss", 45),
      Student2(1, "blny", 34)
    )
    // 指定排序字段 默认升序  优先级大于自带(id)的排序规则
    //val res1 = ls1.sortBy(_.age).reverse
    // 可以使用reverse进行反向排序    如果排序字段是数值  - 反向排序
    val res1 = ls1.sortBy(-_.age)
    // println(ls2.sortBy(- _.id))
    // 非数值类型的属性不能使用 - 反向排序
    println(ls2.sortBy(_.name).reverse)

  }

}
