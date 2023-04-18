package p4_collections.highfunc

import p2_object.{Student, Student2}

/**
 * 对可以排序的集合元素  按照默认排序规则
 * * 对不可以排序的集合元素  会报错
 * * 可以对不可以排序的集合元素 执行排序规则  传入参数new Ordering[Student2] {
 * *          override def compare(x: Student2, y: Student2): Int = {
 * *            -(y.age - x.age)
 * *            }
 * *              }
 * *
 * *   使用ls.sorted  默认排序规则
 */
object C5_SortedFunc {

  def main(args: Array[String]): Unit = {
    val ls = List[Int](2, 1, 3, 45, 32, 5, 6, 9)
    // 默认排序规则
    val res1 = ls.sorted
    val ls2 = List[Student](
      Student(3, "zss", 23),
      Student(2, "lss", 45),
      Student(1, "lny", 34)
    )

    println(ls2)
    // 没有排序规则   不能sorted直接排序
    val ls3 = List[Student2](
      Student2(3, "zss", 23),
      Student2(2, "lss", 45),
      Student2(1, "lny", 34)
    )

    val res4 = ls3.sorted(new Ordering[Student2] {
      override def compare(x: Student2, y: Student2): Int = {
        -(y.age - x.age)
      }
    })

    println(res4)

    //ls3.sorted((stu1, stu2) => stu1.age - stu2.age) //符合函数式编程  不符合参数类型

    // println(res4)

    /*    val res3 = ls3.sorted
        println(res3)*/

    // 按照重写的默认排序UI则
    //val res2 = ls2.sorted
    // println(res2)

  }


}
