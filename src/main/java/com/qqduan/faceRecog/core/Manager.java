package com.qqduan.faceRecog.core;

import java.net.URLEncoder;

import com.qqduan.faceRecog.interfacs.IAdd;
import com.qqduan.faceRecog.interfacs.ICompare;
import com.qqduan.faceRecog.interfacs.IDetectFace;
import com.qqduan.faceRecog.interfacs.Iidentify;
import com.qqduan.faceRecog.interfacs.Iupdate;
import com.qqduan.faceRecog.interfacs.Iverify;
import com.qqduan.faceRecog.util.FileUtil;
import com.qqduan.faceRecog.util.HttpUtil;

public class Manager implements IDetectFace, IAdd, ICompare, Iidentify, Iupdate, Iverify {

	@Override
	public String detectFace(String picPath) {
		String picparam = URLEncoder.encode(FileUtil.FileToBase64(picPath));
		String param = "&image=" + picparam;
		String post = null;
		try {
			post = HttpUtil.post(Defines.DETECT, Defines.TOKEN, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (post != null) {
			return post;
		}
		return null;
	}

	@Override
	public String verify(String uid, String picPath, String group_id) {
		//TODO
		return null;
	}

	@Override
	public String update(String uid, String imgpath, String user_info, String group_id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String identify(String group_id, String imgpath) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String compare(String path1, String path2) {
		String imgParam = URLEncoder.encode(FileUtil.FileToBase64(path1));
		String imgParam2 = URLEncoder.encode(FileUtil.FileToBase64(path2));
		String param = "images=" + imgParam + "," + imgParam2;
		String result = null;
		try {
			result = HttpUtil.post(Defines.COMPARE, Defines.TOKEN, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if(result!=null){
			return result;
		}
		return null;
	}

	@Override
	public String addFace(String uid, String user_info, String group_id, String image) {
		// TODO Auto-generated method stub
		return null;
	}

}
