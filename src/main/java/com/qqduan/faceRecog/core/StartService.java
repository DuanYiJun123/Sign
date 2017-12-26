package com.qqduan.faceRecog.core;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

import com.qqduan.faceRecog.getToken.AuthService;

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
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String token=br.readLine();
			String date=br.readLine();
			if(token==null||date==null){
				AuthService.write();
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
