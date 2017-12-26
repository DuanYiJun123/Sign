package com.qqduan.faceRecog.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.qqduan.faceRecog.getToken.AuthService;
import com.qqduan.faceRecog.util.DateUtil;

public class StartService {

	private static StartService instance = new StartService();

	private StartService() {
	}

	public static StartService newInstance() {
		return instance;
	}

	private void init() {
		File file = new File(Defines.TMPPATH);
		BufferedReader br = null;
		try {
			Date now = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			String nowday = sdf.format(now);
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String token = br.readLine();
			String date = br.readLine();
			if (token == null || date == null || DateUtil.compareDate(date, nowday) >= 7) {
				System.out.println("TOKEN已过期，正在重新自动获取");
				AuthService.write();
			}
			Defines.TOKEN = token;
			if (Defines.TOKEN == null) {
				throw new RuntimeException("获取TOKEN失败");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (br != null) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
