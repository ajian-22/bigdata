package collections.mutable.set

import scala.collection.mutable

/**
 * 1 去重
 * 2 可变和不可变的名字一样  使用包区分
 * 3 没有顺序
 */
object UseDemo {
  def main(args: Array[String]): Unit = {

    // 可变的   包.Set
    val ss = mutable.Set[Int]()
    ss.clear()
    ss.+=(1)
    ss.+=(2,3,4)
    ss.-=(2)
    ss.++=(List[Int](4,5)) //--=
    ss.add(11)
    ss.remove(4) //移除
    //使用HashSet集合  使用mutable区分可变不可变
    val hset = new mutable.HashSet[String]()
    hset.add("tom")
    hset.add("jim")
    hset.remove("lny")
  }
}
