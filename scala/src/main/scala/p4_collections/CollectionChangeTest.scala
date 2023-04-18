package p4_collections

import java.util

import com.alibaba.fastjson.JSON
import com.alibaba.fastjson.serializer.SerializerFeature
/**
 * java 的集合 和  Scala的集合互转
 */
object CollectionChangeTest {
  def main(args: Array[String]): Unit = {

    val javaLi: util.ArrayList[Int] = new util.ArrayList[Int]()

    javaLi.add(889)
    javaLi.add(22)
    javaLi.add(90)
    println(javaLi)

    import scala.collection.JavaConverters._
    println(javaLi.asScala.sum)

    val map = Map("name" -> "tianye", "age" -> 31,"hobby"->"music")

    val res = JSON.toJSONString(map.asJava, SerializerFeature.PrettyFormat)
    println(res)

  }

}
