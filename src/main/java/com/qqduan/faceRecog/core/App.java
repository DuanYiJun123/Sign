package com.qqduan.faceRecog.core;

public class App {

	@SuppressWarnings("deprecation")
	public static void main(String[] args) {
		StartService.newInstance().init();
		SignModule s=new SignModule("E:/test-collection/face/idcard_for_base", "for_test");
		s.start();
	}

}
