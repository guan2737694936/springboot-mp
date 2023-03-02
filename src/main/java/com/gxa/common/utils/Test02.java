package com.gxa.common.utils;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Test02 {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "enM6OTRjZTYyMDBmYzlkNDUzMjZjYmQxM2MyOGUwYWU0ZmM=";

		String decode = Base64Utils.decode(str);
		System.out.println(decode);


	}

	public static String decode(String str){
		byte[] decode = new byte[0];
		try {
			decode = Base64.getDecoder().decode(str.getBytes("utf-8"));
			String str01 = new String(decode,"utf-8");
			return str01;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}

	public static String encode(String str){
		byte[] encode = new byte[0];
		try {
			encode = Base64.getEncoder().encode(str.getBytes("utf-8"));
			String str01 = new String(encode,"utf-8");
			return str01;
		} catch (Exception e) {
			throw new RuntimeException(e);
		}

	}
}
