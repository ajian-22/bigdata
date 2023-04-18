package collections.immutable.array

/**
 * 数组的方法
 * 1   length size  空参方法
 * 2 update(index,newVal)  更新执行位置的元素
 * 3 +:arr  arr:+   在数组的前面 数组的后面追加元素  返回新的数组
 * 4  arr1 ++ arr2  返回一个新的数组  两个数组的并集
 *     union方法一样
 * 5 toList
 * 6 reverse 反转  返回反转后的新的数组
 * 7 min max  sum
 * 8 head 第一个元素 0位置
 * 9 tail  去除第一个元素的子数组
 *
 */
object FuncDemo {

  def main(args: Array[String]): Unit = {

    val arr = Array[Int](1,2,3,4,5)
    val arr2 = Array(2, 3, 4, 5)
    arr.length
    arr.size
    val ints: Array[Int] = arr ++ arr
    val ints1: Array[Int] = arr.union(arr2)
    val reverse: Array[Int] = arr.reverse
    println(arr.max)
    println(arr.min)
    println(arr.sum)
    println(arr.head)
    val tail: Array[Int] = arr.tail
    println(tail.toList)

  }
}
