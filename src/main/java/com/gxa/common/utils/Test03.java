package com.gxa.common.utils;

import com.gxa.modules.sys.entity.User;

import java.io.UnsupportedEncodingException;
import java.util.Base64;

public class Test03 {
	public static void main(String[] args) throws UnsupportedEncodingException {
		String str = "{\"id\":2,\"username\":\"zs\",\"pwd\":\"6ceb3b31d4731ed6f19708fbc2c36df1\",\"salt\":\"456\"}";

		User user = JsonUtils.parseObject(str, User.class);
		System.out.println(user);


	}


}
