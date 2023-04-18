package p5_highlevelfunc

object C6_Implicit {

  //隐式变量  使用 implicit修饰的变量为隐式变量
  //implicit val str1 = "bigdata老师"

  implicit val str2 = "java基础老师"
  // 使用 implicit修饰的参数 为隐式参数
  def show(name: String)(implicit msg: String): Unit = {
    println(s"$name 是$msg")
  }

  def main(args: Array[String]): Unit = {
    show("tianye")("好男人")
    // 在方法调用的时候  隐式参数可以不传递, 自动传:前提是从上下文中 可以找到一个对应的隐式变量
    // 如果上下文中找到两个 会报错
    show("tianye")

  }

}
