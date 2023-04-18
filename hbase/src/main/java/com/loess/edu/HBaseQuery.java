package com.loess.edu;

import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.CompareOperator;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.*;
import org.apache.hadoop.hbase.filter.BinaryComparator;
import org.apache.hadoop.hbase.filter.FilterList;
import org.apache.hadoop.hbase.filter.SingleColumnValueFilter;
import org.apache.hadoop.hbase.util.Bytes;

import java.io.IOException;
import java.util.List;

public class HBaseQuery {
    public static void main(String[] args) throws IOException {
        Connection connection = HBaseUtil.getHBaseConnection();
        Table table = connection.getTable(TableName.valueOf("WATER_BILL"));

        // select  from water_bill where record_date='2020-06-01' and record_date < '2020-07-01'
        SingleColumnValueFilter filter1 = new SingleColumnValueFilter("C1".getBytes(), "RECORD_DATE".getBytes(),
                CompareOperator.GREATER_OR_EQUAL, new BinaryComparator("2020-06-01".getBytes()));
        SingleColumnValueFilter filter2 = new SingleColumnValueFilter("C1".getBytes(), "RECORD_DATE".getBytes(),
                CompareOperator.LESS, new BinaryComparator("2020-07-01".getBytes()));
        FilterList filterList = new FilterList();
        filterList.addFilter(filter1);
        filterList.addFilter(filter2);


        Scan scan = new Scan();
        scan.setFilter(filterList);

        scan.addColumn("C1".getBytes(), "NAME".getBytes());
        scan.addColumn("C1".getBytes(), "RECORD_DATE".getBytes());
        scan.setLimit(10);

        ResultScanner results = table.getScanner(scan);
        for (Result result : results) {
            List<Cell> cells = result.listCells();
            for (Cell cell : cells) {
                String rowKey = Bytes.toString(CellUtil.cloneRow(cell));
                String family = Bytes.toString(CellUtil.cloneFamily(cell));
                String qualifier = Bytes.toString(CellUtil.cloneQualifier(cell));
                String clouValue = Bytes.toString(CellUtil.cloneValue(cell));
                System.out.println(rowKey + "," + family + "," + qualifier + "," + clouValue);
            }
        }
        HBaseUtil.closeHBaseConnection(connection);
    }
}
