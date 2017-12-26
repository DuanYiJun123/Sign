package com.qqduan.faceRecog.core;

import java.net.URLEncoder;

import com.qqduan.faceRecog.util.FileUtil;
import com.qqduan.faceRecog.util.HttpUtil;

public class App {	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		
		String base64 = URLEncoder.encode(FileUtil.FileToBase64("D:/5.jpg"));
		String param= "&image=" + base64;
		try {
			String post = HttpUtil.post(Defines.DETECT, Defines.TOKEN, param);
			System.out.println(post);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
