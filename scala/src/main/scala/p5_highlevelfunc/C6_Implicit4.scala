package p5_highlevelfunc

import java.io.File


object C6_Implicit4 {

  // 导入隐式变量
  import C6_MyImplicit.opt2

  def testValue(implicit x: Int): Unit = {
    println(x)
  }
  def testValue2(implicit x: String): Unit = {
    println(x)
  }
  // 方法的参数列表中有个隐式的参数[隐式函数]
  def m1(str: String, x: Int)(implicit f: (String, Int) => String): String = {
    f(str, x)
  }
  def main(args: Array[String]): Unit = {
    println(m1("zss", 23))
    // testValue2
    // 导入隐式函数
    import C6_MyImplicit.richFile // 函数 [File]->BufferedSource
    // 增强类 装饰者模式
    import C6_MyImplicit.MyFile // 隐式类[File] add()  show()
    val file = new File("/Users/duyaoyao/work/data/wc.txt")

    // 获取到了 BufferedSource中的函数
    file.getLines()
    // 具备了 隐式类的函数
    file.add()
    file.readData()
    file.show()
  }

}
