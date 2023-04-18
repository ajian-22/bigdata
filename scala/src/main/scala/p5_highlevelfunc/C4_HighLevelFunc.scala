package p5_highlevelfunc

/**
 * 高阶函数 : 方法的参数中有一个参数是函数
 * 方法的返回值是函数
 **/
object C4_HighLevelFunc {

  //高阶函数  数据的处理逻辑可以动态的传入
  // 强大 灵活
  // 方便 传入灵活的数据处理逻辑 ......
  def optInt(x: Int, y: Int)( f: (Int, Int) => Int): Int = {
    f(x, y)
  }

  /**
   * 方法的返回值是函数
   *
   * @param ssl
   * @param domain
   * @return
   */
  //  http://www.gupao.cn?name=zss
  def mkUrl(ssl: Boolean, domain: String): (String, String) => String = {
    val xieyi = if (ssl) "https" else "http"
    // 匿名函数
    (key: String, search: String) => s"$xieyi://$domain?$key=$search"
  }


  def main(args: Array[String]): Unit = {
    val str1: String = mkUrl(true, "www.doitedu.cn")("mk1", "mv")
    val str2: String = mkUrl(true, "www.doitedu.cn")("mk2", "mv2")
    println(str1)
    println(str2)


    val f: (String, String) => String = mkUrl(true, "www.gupao.cn")
    val str: String = f("name", "美女")
    val strr: String = f("name2", "美女")
    println(str)
    println(strr)
    //  https://www.gupao.cn?name=美女



    // println(optInt(2, 5,(x, y) => x * y))
    println(optInt(2, 5)((x, y) => x * y))

  }

}
