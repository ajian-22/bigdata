package collections.immutable.array


/**
 * 数组的定义
 */
object DefineDemo {

  def main(args: Array[String]): Unit = {
    // 不可变的数组   默认  不能对数组的长度进行改变
    val arr1: Array[Any] = Array("zss",23,'a' , true)

    // 自动推导数据类型
    val arr2: Array[Any] =  Array("zss",23,'a' , true)
    val arr3 = Array[Int](1,2,3,4,5)

    arr1.length  // 长度
    arr1.toList  // 转换成List  toString
    println(arr1(0))
    arr1.update(0 , "ww") // 更新指定位置的元素
    arr1(0) = "lny"  //  更新指定位置的元素
    println(arr1(0))
    println("##############################")
    arr1.reverse // 反转


    val arr7 = "haha" +: arr1
    println("arr7:" + arr7.toList)
    println("arr1:" + arr1.toList)

    // arr1:+111  :+  +: 返回新的数组
    val array: Array[Any] = arr1 :+ 1111
    val array2 = 222+:arr1
    println(arr1.toList)
    println(array.toList)
    println(array2.toList)

  }
}
