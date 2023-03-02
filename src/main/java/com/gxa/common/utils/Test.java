package com.gxa.common.utils;

import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.util.ByteSource;

public class Test {
	public static void main(String[] args) {
		String hashAlgorithmName = "MD5";
		Object credentials = "admin";
		
		
//		Object result = new SimpleHash(hashAlgorithmName,credentials);
//		System.out.println(result);
		
		
//		Object result = new SimpleHash(hashAlgorithmName,credentials,null,1024);
//		System.out.println(result);
		
		Object salt = ByteSource.Util.bytes("789");;
		Object result = new SimpleHash(hashAlgorithmName, credentials, salt, 1024);
		System.out.println(result);
	}
}
