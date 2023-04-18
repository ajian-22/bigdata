package p4_collections.highfunc

import p2_object.{Student, Student2}

/**
 *
 */
object C5_SortWithFunc {

  def main(args: Array[String]): Unit = {

    val ls1  = List[Student](
      Student(3,"zss",23) ,
      Student(2,"lss",45) ,
      Student(1,"lny" ,34)
    )
    // 没有排序规则   不能sorted直接排序
    val ls2 = List[Student2](
      Student2(3,"azss",23) ,
      Student2(2,"xlss",45) ,
      Student2(1,"blny" ,34)
    )

    val res = ls2.sortWith((stu1, stu2) => stu1.name < stu2.name)
    println(res)


  }

}
