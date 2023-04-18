package com.loess.edu;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

public class HBaseTest {
    public static void main(String[] args) throws IOException {
        Connection connection = HBaseUtil.getHBaseConnection();

        Admin admin = connection.getAdmin();
        boolean water_bill = admin.tableExists(TableName.valueOf("WATER_BILL"));
        if (!water_bill) {
            ColumnFamilyDescriptor columnFamilyDescriptor = ColumnFamilyDescriptorBuilder.newBuilder("C1".getBytes()).build();
            TableDescriptor tableDescriptor = TableDescriptorBuilder.newBuilder(TableName.valueOf("WATER_BILL"))
                    .setColumnFamily(columnFamilyDescriptor).build();
            admin.createTable(tableDescriptor);
        }
        addData(connection);
        HBaseUtil.closeHBaseConnection(connection);
    }

    public static void addData(Connection connection) throws IOException {
        Table table = connection.getTable(TableName.valueOf("WATER_BILL"));

        int count = queryByRowKey(table);
        if (count != 0) {
//            delete(table);
            return;
        }
        Put put = new Put("3919700".getBytes());
        put.addColumn("C1".getBytes(), "name".getBytes(), "茹喜兰".getBytes());
        put.addColumn("C1".getBytes(), "address".getBytes(), "江西省新余市分宜县钤山镇6单元251室".getBytes());
        put.addColumn("C1".getBytes(), "gendar".getBytes(), "男".getBytes());
        put.addColumn("C1".getBytes(), "record_time".getBytes(), "2019/6/24".getBytes());
        put.addColumn("C1".getBytes(), "cur_num".getBytes(), "62.5".getBytes());
        put.addColumn("C1".getBytes(), "last_num".getBytes(), "40.5".getBytes());
        put.addColumn("C1".getBytes(), "num_usage".getBytes(), "22".getBytes());
        put.addColumn("C1".getBytes(), "money".getBytes(), "132".getBytes());
        table.put(put);
//        HBaseUtil.closeTable(table);
    }

    public static int queryByRowKey(Table table) throws IOException {
        Get get = new Get("4944191".getBytes());
//        get.addColumn("C1".getBytes(), "name".getBytes());
        Result result = table.get(get); // null
        List<Cell> cells = result.listCells();
        for (Cell cell : cells) {
            String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
            String family = Bytes.toString(CellUtil.cloneFamily(cell));
            String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
            String clouValue = Bytes.toString(CellUtil.cloneValue(cell));
            System.out.println(rowKey + "," + family + "," + qualifier + "," + clouValue);
        }
        return cells.size();
    }

    public static void delete(Table table) throws IOException {
        Delete delete = new Delete("4944191".getBytes());
//        delete.addFamily("C1".getBytes());
//        delete.addColumn("C1".getBytes(), "name".getBytes());
        table.delete(delete);
    }

    public static void dropTable(Admin admin) throws IOException {
        boolean flag = admin.isTableDisabled(TableName.valueOf("WATER_BILL"));
        if (!flag) {
            admin.disableTable(TableName.valueOf("WATER_BILL"));
        }
        admin.deleteTable(TableName.valueOf("WATER_BILL"));
    }
}
