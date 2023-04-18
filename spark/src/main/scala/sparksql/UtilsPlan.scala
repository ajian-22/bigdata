package sparksql

import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import utils.SparkUtil


object UtilsPlan {

  def main(args: Array[String]): Unit = {
    val session = SparkUtil.getSession
    // 解析SQL语法后生成的逻辑执行计划 , 没有绑定元数据  没有优化
    /**
     * 'Project ['t1.id, 't2.id AS id2#0, 't1.name, 't1.city, 't2.ct]
     * +- 'Filter ('t2.id > 3)
     * +- 'Join Inner, ('t1.id = 't2.id)
     * :- 'SubqueryAlias t1
     * :  +- 'Project [*]
     * :     +- 'Filter ('t.id > 2)
     * :        +- 'UnresolvedRelation [t], [], false
     * +- 'UnresolvedRelation [t2], [], false
     */

    val  sql =
      """
        |select
        |t1.id ,
        |t2.id as id2 ,
        |t1.name ,
        |t1.city  ,
        |t2.ct
        |from
        |(select *  from t where t.id > 2) t1
        |join
        |t2
        |on  t1.id = t2.id
        |where  t2.id  > 3
        |""".stripMargin

    println("-------------------------------------")
    val plan: LogicalPlan = session.sessionState.sqlParser.parsePlan(sql)
    println(plan)
    println("-------------------------------------")

    /**
     * 会话状态
     * 1. 使用Parser解析器 : 解析SQL语句和DF,DF语义--->没有绑定元数据的逻辑执行计划
     * 逻辑执行计划: 抽象的语法树 ,不能执行 , 描述了执行的顺序 注意:只有物理执行计划才能执行
     * 2.  Analyzer 绑定sessioncatalog  绑定元数据信息 [数据来源, 字段 ,字段类型] --->逻辑执行计划
     * 3. Optimizer 优化器 , 优化你的SQL语句   优化后的逻辑执行计划
     * 4. Planer  将优化后的逻辑执行计划转换成物理执行计划
     * 5. Execution将物理执行计划转换成  DAGS  [RDD]
     */
    session.sessionState.sqlParser  // 解析sql语句
    session.sessionState.analyzer   // 分析  分析语法   语法树
    //session.sessionState.catalogManager  // 元数据信息
    session.sessionState.optimizer  // 优化器
    session.sessionState.planner  // 计划 逻辑计划  物理计划
    //session.sessionState.queryStagePrepRules  //  规则
    // session.sessionState.executePlan()  QueryExecution

    val df1 = session.read.json("data/json/stu.json")
    val df2 = session.read.json("data/json/score.json")


    df1.createOrReplaceTempView("tb_stu")
    df2.createOrReplaceTempView("tb_score")


    val resDF = session.sql(
      """
        |select
        | t1.sid,
        | t1.sname ,
        | t1.age ,
        | t1.gender ,
        | t2.subject ,
        | t2.score
        |from
        |(
        |   select
        |   *
        |   from
        |   tb_stu
        |   where sid > 1
        |) t1
        |join
        |(
        |   select
        |   *
        |   from
        |   tb_score
        |   where sid > 1
        |) t2
        |on  t1.sid = t2.sid
        |
        |
        |""".stripMargin)

    //resDF.explain("extended")
    session.close()

  }

}
