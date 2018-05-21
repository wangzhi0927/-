package com.slzr.ureport.bean;

import java.util.ArrayList;
import java.util.List;

public class test {
    public static void main(String[] args) {
        String data="84652";
        String result="QL"+addDate(data);


        System.out.println(result); // 0000000001
    }

    public  static  String  addDate(String data){
        String constant="0000000000";

        return (constant+data).substring((constant+data).length()-10,(constant+data).length());


    }
}