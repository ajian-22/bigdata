package p4_collections.highfunc


object C1_FlattenMapFunc {

  def main(args: Array[String]): Unit = {

    val li=List("scala python golang java c","sql scala python","sql js php scala")

    //map + flatten
    val res = li.map(_.split(" ")).flatten
   // println(res)

    //flatten
    val res2=li.flatMap(_.split(" "))
    println(res2)
  }

}
