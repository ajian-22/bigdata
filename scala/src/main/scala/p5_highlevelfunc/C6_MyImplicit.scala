package p5_highlevelfunc

import java.io.File

import scala.io.{BufferedSource, Source}


object C6_MyImplicit {

  // 隐式变量
  implicit val x: Int = 100
  implicit val str = "doitedu"

  // 隐式方法
  implicit def optInt(x: Int, y: Int): Int = {
    x + y
  }

  implicit def opt2(str: String, x: Int): String = {
    str + x
  }

  // 文件具有 BufferedSource类的方法
  implicit def richFile(file: File): BufferedSource = {
    Source.fromFile(file)
  }

  // 类似于装饰者模式   包装一个类 使其功能增强
  implicit class MyFile(f: File) {

    def add(): Unit = {

    }

    def show(): Unit = {
      println("MYFile.....")
    }

    def readData(): Unit = {


    }
  }


}
