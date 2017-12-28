package com.qqduan.faceRecog.core;

import java.io.File;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSONObject;

public class SignModule {

	private Manager m;
	private Set<String> pics;
	private ExecutorService service = Executors.newFixedThreadPool(4);
	private File file;
	private boolean flag = false;

	private String uid;
	private String group_id;

	public SignModule(String path, String uid, String group_id) {
		m = new Manager();
		pics=new HashSet<>();
		this.file = new File(path);
		if (!file.isDirectory()) {
			throw new RuntimeException("file is not a directory");
		}
		this.uid = uid;
		this.group_id = group_id;
	}

	public void initSign() {
		int num = 1;
		File[] listFiles = this.file.listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			String name = listFiles[i].getName();
			if (name.endsWith("jpg") || name.endsWith("png") || name.endsWith("JPG")|| name.endsWith("tif")) {
				pics.add(listFiles[i].getAbsolutePath());
				System.out.println("正在初始化照片  " + name + "====" + num);
				num++;
			}
		}
	}

	public void add() {
		AtomicInteger succ = new AtomicInteger(1);
		AtomicInteger fail = new AtomicInteger(1);
		Iterator<String> it = pics.iterator();
		for (int i = 0; i < 4; i++) {
			service.execute(() -> {
				while (it.hasNext()) {
					String next = it.next();
					String result = m.addFace(uid, "注册照片", group_id, next);
					JSONObject json = JSONObject.parseObject(result);
					if (json.containsKey("error_code")) {
						System.out.println("注册照片  " + next + " 失败" + fail.getAndIncrement());
					} else {
						System.out.println("注册照片  " + next + " 成功" + succ.getAndIncrement());
					}
				}
			});
			service.shutdown();
		}
		while (!service.isTerminated()) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		System.out.println("注册完成");
	}

	public boolean checkGroup() {
		String users = m.getUsers(group_id, 1, 100);
		JSONObject json = JSONObject.parseObject(users);
		String result = json.getString("result");
		if (result != null) {
			return true;
		} else {
			return false;
		}
	}

	public void verify(String picPath) {
		String verify = m.verify(uid, picPath, group_id);
		JSONObject json = JSONObject.parseObject(verify);
		String string = json.getString("result");
		String substring = string.substring(0, string.length());
		Integer score = Integer.valueOf(substring);
		if (score >= 80) {
			System.out.println(picPath + "  签到成功！");
		}

	}

}
