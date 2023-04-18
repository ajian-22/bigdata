package p5_highlevelfunc

import java.io.File

import scala.io.{BufferedSource, Source}


object C6_Implicit3 {

  implicit val f = (x: Int, y: Int) => {
    x + y
  }

  def opt(x: Int, y: Int)(implicit f: (Int, Int) => Int): Int = {
    f(x, y)
  }

  // 隐式方法  (输入数据) =>( 返回数据)
  // File => BufferedSource
  // 隐式函数  当使用 File的时候 发现上下文中有一个 File => BufferedSource 方法  隐式转换
  // 对类进行功能项的增强和扩展
 implicit  def richFile(file: File): BufferedSource = {
    val bs: BufferedSource = Source.fromFile(file)
    bs
  }

  // 接收一种A类型  返回另一种B类型  具有了另一种类型的功能和属性 A . m2
  implicit def richA(a: A): B = {
    new B
  }

  def main(args: Array[String]): Unit = {
    //println(opt(3,5))
    val file: File = new File("/Users/duyaoyao/work/data/wc.txt")


    //隐式转换
    val lines: Iterator[String] = file.getLines()
    lines.foreach(println)
    val a = new A
    a.m1()
    a.m2()
  }

}

class A {
  def m1() = {
    println("m1....")
  }

}

class B {
  def m2() = {
    println("m1....")
  }

}
