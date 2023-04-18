package com.loess.edu.utils;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HdfsUtil {
    public static String hdfsURl = "hdfs://node1:8020";

    public static FileSystem getFileSystem() {
        FileSystem fileSystem;
        try {
            fileSystem = FileSystem.get(new URI(hdfsURl), new Configuration(),"root");
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return fileSystem;
    }
}
