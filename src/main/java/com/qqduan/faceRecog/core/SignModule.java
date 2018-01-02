package com.qqduan.faceRecog.core;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSONObject;
import com.qqduan.faceRecog.util.FileUtil;

public class SignModule {

	private Manager m;
	private File file;
	private boolean flag = true;

	private Map<String, String> map;
	private String group_id;

	public SignModule(String path, String group_id) {
		m = new Manager();
		map = new ConcurrentHashMap<>();
		this.file = new File(path);
		if (!file.isDirectory()) {
			throw new RuntimeException("file is not a directory");
		}
		this.group_id = group_id;
	}

	public void start() {
		initSign();
		add();
		while (flag) {
			flag = checkGroup();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String picPath = null;
			try {
				picPath = br.readLine();
			} catch (IOException e) {
				e.printStackTrace();
			}
			identify(picPath);
			if (picPath.equals("done")) {
				flag = false;
			}
		}
		write();
		System.out.println("结果已保存");
	}

	public void initSign() {
		int num = 1;
		File[] listFiles = this.file.listFiles();
		for (int i = 0; i < listFiles.length; i++) {
			String name = listFiles[i].getName();
			if (name.endsWith("jpg") || name.endsWith("png") || name.endsWith("JPG") || name.endsWith("tif")) {
				map.put(name, listFiles[i].getAbsolutePath());
				System.out.println("正在初始化照片  " + name + "====" + num);
				num++;
			}
		}
	}

	public void add() {
		AtomicInteger succ = new AtomicInteger(1);
		AtomicInteger fail = new AtomicInteger(1);
		Iterator<Entry<String, String>> it = map.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, String> next = it.next();
			String[] split = next.getKey().split("\\.");
			String result = m.addFace(split[0], "注册照片", group_id, next.getValue());
			JSONObject json = JSONObject.parseObject(result);
			if (json.containsKey("error_code")) {
				System.out.println("注册照片  " + next + " 失败" + fail.getAndIncrement());
			} else {
				System.out.println("注册照片  " + next + " 成功" + succ.getAndIncrement());
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

	public void identify(String picPath) {
		String identify = m.identify(group_id, picPath, 1);
		JSONObject json = JSONObject.parseObject(identify);
		String string = json.getString("result");
		String substring = string.substring(1, string.length() - 1);
		JSONObject json1 = JSONObject.parseObject(substring);
		String string2 = json1.getString("scores");
		Integer score = Integer.valueOf(string2);
		String name = json1.getString("uid");
		if (score >= 80) {
			if (map.containsKey(name)) {
				System.out.println(name + " 签到成功");
				map.remove(name);
			}
		} else {
			System.out.println(name + " 没有签到！");
		}
	}

	public void write() {
		File file = new File(FileUtil.getAppRoot() + File.separator + "resource" + File.separator + "name.txt");
		if (file.exists()) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		BufferedWriter wr = null;
		try {
			wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file)));
			if (!map.isEmpty()) {
				Iterator<Entry<String, String>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> next = it.next();
					wr.write(next.getKey());
					wr.flush();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (wr != null) {
				try {
					wr.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
