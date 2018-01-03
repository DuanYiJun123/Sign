package com.qqduan.faceRecog.core;

public class App {

	public static void main(String[] args) {
		StartService.newInstance().init();
		SignModule s = new SignModule(Defines.path, Defines.group_id);
		s.start();
	}
}
