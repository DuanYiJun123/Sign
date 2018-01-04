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
import java.util.concurrent.atomic.AtomicInteger;

import com.alibaba.fastjson.JSONObject;
import com.qqduan.faceRecog.util.FileUtil;

public class SignModule {

	private Manager m;
	private File file;
	private boolean flag = false;

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
		flag = checkGroup();
		if (!flag) {
			System.out.println("组不存在，创建组并添加人脸");
			try {
				System.out.println("准备初始化人脸，并添加人脸");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			initSign();
			add();
			flag = checkGroup();
		}
		if (flag) {
			System.out.println("组已存在");
			try {
				System.out.println("准备初始化人脸");
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			initSign();
		}
		while (flag) {
			flag = checkGroup();
			BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
			String picPath = null;
			String replaceAll = null;
			try {
				System.out.println("请输入人脸图片地址： ");
				picPath = br.readLine();
				if (picPath.equals("done")) {
					flag = false;
				} else {
					replaceAll = picPath.replaceAll("\\\\", "/");
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			if (replaceAll != null) {
				identify(replaceAll);
			}
		}
		write();
		System.out.println("结果已保存");
	}

	public void initSign() {
		int num = 1;
		File[] listFiles = this.file.listFiles();
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
			wr.write("=========全部人员=========");
			wr.newLine();
			wr.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (int i = 0; i < listFiles.length; i++) {
			String name = listFiles[i].getName();
			if (name.endsWith("jpg") || name.endsWith("png") || name.endsWith("JPG") || name.endsWith("tif")) {
				String[] split = name.split("\\.");
				map.put(split[0], listFiles[i].getAbsolutePath());
				try {
					wr.write(name);
					wr.newLine();
					wr.flush();
				} catch (IOException e) {
					e.printStackTrace();
				}
				System.out.println("正在初始化照片  " + name + "====" + num);
				num++;
			}
		}
		if (map.isEmpty()) {
			System.out.println("图片集路径中没有要注册的图片，请检查config.xml,并重新运行");
			System.exit(0);
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
				System.out.println("失败信息 " + result);
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
		if (!result.equals("[]")) {
			return true;
		} else {
			return false;
		}
	}

	public void identify(String picPath) {
		File file = new File(picPath);
		if (file.isDirectory()) {
			File[] listFiles = file.listFiles();
			for (int i = 0; i < listFiles.length; i++) {
				String name = listFiles[i].getName();
				if (name.endsWith("jpg") || name.endsWith("png") || name.endsWith("JPG") || name.endsWith("tif")) {
					identy(listFiles[i].getAbsolutePath());
				}
			}
		} else {
			identy(picPath);
		}
	}

	private void identy(String picPath) {
		String identify = m.identify(group_id, picPath, 1);
		JSONObject json = JSONObject.parseObject(identify);
		String string = json.getString("result");
		if (string == null) {
			System.out.println("请重新输入正确的人脸图片地址");
		} else {
			String substring = string.substring(1, string.length() - 1);
			JSONObject json1 = JSONObject.parseObject(substring);
			String string2 = json1.getString("scores");
			String substring2 = string2.substring(1, string2.length() - 1);
			Float score = Float.valueOf(substring2);
			String name = json1.getString("uid");
			if (score >= 80) {
				if (map.containsKey(name)) {
					System.out.println(name + " 签到成功");
					System.out.println("分数值为:" + score);
					map.remove(name);
				} else {
					System.out.println("注册照中没有该人 " + name);
				}
			} else {
				System.out.println(name + " 没有签到！");
				System.out.println("最高分数值为：" + score);
			}
		}
	}

	public void write() {
		File file = new File(FileUtil.getAppRoot() + File.separator + "resource" + File.separator + "name.txt");
		if (!file.exists()) {
			throw new RuntimeException("name.txt not exist!");
		}
		BufferedWriter wr = null;
		try {
			wr = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file, true)));
			if (!map.isEmpty()) {
				wr.write("=========未签到=========");
				wr.newLine();
				wr.flush();
				Iterator<Entry<String, String>> it = map.entrySet().iterator();
				while (it.hasNext()) {
					Entry<String, String> next = it.next();
					wr.write(next.getKey());
					wr.newLine();
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
