package com.qqduan.faceRecog.core;

import java.util.HashSet;

import com.alibaba.fastjson.JSONObject;

public class App {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		StartService.newInstance().init();
		Manager m = new Manager();
		// System.out.println(m.compare("D:/5.jpg", "D:/5.jpg"));
		// System.out.println(m.detectFace("D:/5.jpg"));
//		String addFace = m.addFace("qqduan", "haha", "me", "D:/5.jpg");
//		m.addFace("qqduan", "haha", "me", "D:/段毅君/usa/IMG_0014.JPG");
		//System.out.println(addFace);

		// System.out.println(m.getlist(0, 10));
		// System.out.println(m.identify("me", "D:/5.jpg", 1));
		// String verify = m.verify("qqduan", "D:/IMG_3265.JPG", "me");
		// JSONObject json=JSONObject.parseObject(verify);
		// String string = json.getString("result");
		// System.out.println(string);
//		String query = m.query("qqduan", "me");
//		System.out.println(query);
		String users = m.identify("me", "D:/段毅君/usa/IMG_0014.JPG", 1);
		JSONObject json=JSONObject.parseObject(users);
		String string = json.getString("result");
		String substring = string.substring(1, string.length()-1);
		JSONObject json1=JSONObject.parseObject(substring);
		//String string2 = json1.getString("uid");
		System.out.println(substring);
	}

}
