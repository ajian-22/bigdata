package com.loess.edu.mapreduce_partition;

public class TestDemo {
    public static void main(String[] args) {
        String str1 = "hello";
        String str2 = "word";
        String str3 = "hadoop";

        System.out.println(str1.hashCode());
        System.out.println(str2.hashCode());
        System.out.println(str3.hashCode() & 2147483647);
    }
}
