package com.qqduan.faceRecog.core;

import com.alibaba.fastjson.JSONObject;

public class App {	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		StartService.newInstance().init();
		Manager m=new Manager();
		//System.out.println(m.compare("D:/5.jpg", "D:/5.jpg"));
		//System.out.println(m.detectFace("D:/5.jpg"));
		//System.out.println(m.addFace("qqduan", "haha", "me", "D:/IMG_3265.JPG"));
		//System.out.println(m.getlist(0, 10));
		//System.out.println(m.identify("me", "D:/5.jpg", 1));
		String verify = m.verify("qqduan", "D:/IMG_3265.JPG", "me");
		JSONObject json=JSONObject.parseObject(verify);
		String string = json.getString("result");
		System.out.println(string);
	}

}
