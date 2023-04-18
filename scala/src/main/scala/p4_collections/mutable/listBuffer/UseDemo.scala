package collections.mutable.listBuffer

object UseDemo {

  def main(args: Array[String]): Unit = {
    // 可变的List集合  _ 所有的类   val f = 方法 _   x=>x  _
    import  scala.collection.mutable._
    val lb = ListBuffer[String]()
    val lb2 = ListBuffer[String]()

    lb.append("a","b","c")
    lb.append("d")
    lb.remove(0,2)
    lb.clear()
    lb.+=("tom").+=("jim").+=("cat") += "xixi"
    lb2.+=("hah").+=("gege").+=("xixi")

    // -= ++= --=
    println(lb)
    println(lb--=lb2)
  }
}
