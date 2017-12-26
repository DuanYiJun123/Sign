package com.qqduan.faceRecog.core;

public class App {	
	
	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		StartService.newInstance().init();
		Manager m=new Manager();
		System.out.println(m.detectFace("D:/5.jpg"));
	}

}
