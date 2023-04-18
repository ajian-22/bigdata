package collections.highfunc

/**
 * count(t:T=>Boolean)
 * 统计集合中符合条件的元素的个
 */
object C4_SumCountFunc {

  def main(args: Array[String]): Unit = {
    //sum
    val arr1=Array(1, 2, 345, 67, 5)
    println(arr1.sum)

    val ls1 = List(1, 2, 345, 67, 5)
    ls1.sum

    val set = Set(1, 2, 345, 67, 5)
    set.sum


    //count
    val arr = Array(1, 2, 345, 67, 5)
    val ls = List("Jim", "TomCat", "Cat", "tom")
    val strings = ls.filter(_.equalsIgnoreCase("tomcat"))
    println(strings)
    /* val i = ls.count(_.contains("h"))
     println(i)*/
    /*println(arr.filter(_ < 3).size)
    val i: Int = arr.count(_ < 3)
    println(i)
*/

  }
}
