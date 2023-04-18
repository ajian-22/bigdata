package com.loess.edu;

import com.loess.edu.utils.HdfsUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
public class HDFSOperations {
    @Test
    public void listFiles() throws IOException {
        FileSystem fileSystem = HdfsUtil.getFileSystem();
        RemoteIterator<LocatedFileStatus> locatedFileStatusRemoteIterator = fileSystem.listFiles(new Path("/"), true);
        while (locatedFileStatusRemoteIterator.hasNext()) {
            LocatedFileStatus next = locatedFileStatusRemoteIterator.next();
            System.out.println(next.getPath().toString());
        }
        fileSystem.close();
    }

    @Test
    public void mkdir() throws IOException {
        FileSystem fileSystem = HdfsUtil.getFileSystem();
        boolean mkdirs = fileSystem.mkdirs(new Path("/data/hbase/"));
        System.out.println(mkdirs);
    }

    @Test
    public void getFileToLocal() throws IOException {
        FileSystem fileSystem = HdfsUtil.getFileSystem();
        FSDataInputStream inputStream = fileSystem.open(new Path("/data/covid1.dat"));
        FileOutputStream outputStream = new FileOutputStream(new File("D:\\timer.dat"));
        IOUtils.copy(inputStream, outputStream);
        inputStream.close();
        outputStream.close();
        fileSystem.close();
    }

    @Test
    public void getFileToLocal1() throws IOException {
        FileSystem fileSystem = HdfsUtil.getFileSystem();
        fileSystem.copyToLocalFile(new Path("/export/output/XML.har"), new Path("D:\\XML.har"));
        fileSystem.close();
    }

    @Test
    public void putData() throws IOException {
        FileSystem fileSystem = HdfsUtil.getFileSystem();
        fileSystem.copyFromLocalFile(new Path("G:\\BIGDATA\\HBase\\资料\\数据集\\抄表数据\\part-m-00000_10w"), new Path("/data/hbase/"));
        fileSystem.close();
    }

    /**
     * 小文件合并之后上传HDFS
     * @throws IOException
     */
    @Test
    public void mergeFile() throws IOException {
        FileSystem fileSystem = HdfsUtil.getFileSystem();
        FSDataOutputStream outputStream = fileSystem.create(new Path("/data/covid5.dat"));
        //获取本地文件系统
        LocalFileSystem localFileSystem = FileSystem.getLocal(new Configuration());
        //通过本地文件系统获取文件列表，为一个集合
        FileStatus[] statuses = localFileSystem.listStatus(new Path("G:\\BIGDATA\\test_data\\datasets\\mergeFile"));
        for (FileStatus fileStatus : statuses) {
            System.out.println(fileStatus.getPath());
            FSDataInputStream fsDataInputStream = localFileSystem.open(fileStatus.getPath());
            IOUtils.copy(fsDataInputStream, outputStream);
            fsDataInputStream.close();
        }
        outputStream.close();
        fileSystem.close();
    }

}
