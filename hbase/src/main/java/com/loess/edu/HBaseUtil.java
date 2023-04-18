package com.loess.edu;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.client.Admin;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Table;

import java.io.IOException;

public class HBaseUtil {
    public static Connection getHBaseConnection() throws IOException {
        Configuration configuration = HBaseConfiguration.create();
        configuration.set("hbase.zookeeper.quorum", "node1:2181,node2:2181,node3:2181");

        Connection connection = ConnectionFactory.createConnection(configuration);
        return connection;
    }

    public static void closeHBaseConnection(Connection connection) throws IOException {
        if (connection != null) {
            connection.close();
        }
    }

    public static void closeAdmin(Admin admin) throws IOException {
        if (admin != null) {
            admin.close();
        }
    }

    public static void closeTable(Table table) throws IOException {
        if (table != null) {
            table.close();
        }
    }
}
