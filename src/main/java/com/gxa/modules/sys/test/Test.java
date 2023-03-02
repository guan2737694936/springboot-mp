package com.gxa.modules.sys.test;

import com.gxa.common.utils.ErrorCode;
import com.gxa.common.utils.Result;
import org.apache.shiro.crypto.hash.SimpleHash;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {
    public static void main(String[] args) throws ParseException {
//        String pwd = new SimpleHash("MD5", "4444", "null", 1024).toString();
        String pwd = new SimpleHash("MD5", "1111", null, 1024).toString();
        System.out.println(pwd);
//        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//        String time = sdf.format(Date.parse("Thu Jul 22 00:58:32 CST 2010"));
//        System.out.println(time);
//        String state = "2";
//        if (!state.equals("1")){
//            System.out.println(111);
//        }else {
//            System.out.println(222222);
//        }
    }
}
