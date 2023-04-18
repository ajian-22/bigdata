package com.loess.edu.sql;

import com.loess.edu.utils.SqlHolder;
import org.apache.flink.table.api.EnvironmentSettings;
import org.apache.flink.table.api.TableEnvironment;

import java.io.IOException;

/**
 * 直接表去关联Kafka中的数据
 */
public class KafkaSqlConnectorTest {
    public static void main(String[] args) throws IOException {

        //StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        //StreamTableEnvironment tEnv = StreamTableEnvironment.create(env);

        EnvironmentSettings settings = EnvironmentSettings.newInstance().inStreamingMode().build();
        TableEnvironment tEnv = TableEnvironment.create(settings);

        // 把外部数据源（kafka），利用connector，注册成一个视图（表）
        tEnv.executeSql(SqlHolder.getSql(1));  // 执行该建表语句后，系统的元数据中就存在了这张表



        // sql 查询
        // 筛选出action_timelong>10的数据
        tEnv.executeSql("select * from action_event where action_timelong>10").print();

        //

    }
}

