package com.qqduan.faceRecog.core;

import java.net.URLEncoder;

import com.qqduan.faceRecog.interfacs.IAdd;
import com.qqduan.faceRecog.interfacs.ICompare;
import com.qqduan.faceRecog.interfacs.IDetectFace;
import com.qqduan.faceRecog.interfacs.IMulti;
import com.qqduan.faceRecog.interfacs.IQuery;
import com.qqduan.faceRecog.interfacs.Idelete;
import com.qqduan.faceRecog.interfacs.IgetUsers;
import com.qqduan.faceRecog.interfacs.Igetlist;
import com.qqduan.faceRecog.interfacs.Iidentify;
import com.qqduan.faceRecog.interfacs.Iupdate;
import com.qqduan.faceRecog.interfacs.Iverify;
import com.qqduan.faceRecog.util.FileUtil;
import com.qqduan.faceRecog.util.HttpUtil;

public class Manager implements IDetectFace, IAdd, ICompare, Iidentify, Iupdate, Iverify, Idelete, Igetlist, IMulti,
		IQuery, IgetUsers {

	@Override
	public String detectFace(String picPath) {
		String picparam = URLEncoder.encode(FileUtil.FileToBase64(picPath));
		String param = "&image=" + picparam;
		String post = null;
		try {
			post = HttpUtil.post(Defines.DETECT, param);
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
		String picparam = URLEncoder.encode(FileUtil.FileToBase64(picPath));
		String param = "group_id=" + group_id + "&uid=" + uid + "&images=" + picparam;
		String result = null;
		try {
			result = HttpUtil.post(Defines.VERIFY, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String update(String uid, String imgpath, String user_info, String group_id) {
		String picparam = URLEncoder.encode(FileUtil.FileToBase64(imgpath));
		String param = "uid=" + uid + "&user_info" + user_info + "&images=" + picparam;
		String result = null;
		try {
			result = HttpUtil.post(Defines.UPDATE, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String identify(String group_id, String imgpath, int user_top_num) {
		String picparam = URLEncoder.encode(FileUtil.FileToBase64(imgpath));
		String param = "group_id=" + group_id + "&user_top_num=" + user_top_num + "&images=" + picparam;
		String result = null;
		try {
			result = HttpUtil.post(Defines.IDENTIFY, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String compare(String path1, String path2) {
		String imgParam = URLEncoder.encode(FileUtil.FileToBase64(path1));
		String imgParam2 = URLEncoder.encode(FileUtil.FileToBase64(path2));
		String param = "images=" + imgParam + "," + imgParam2;
		String result = null;
		try {
			result = HttpUtil.post(Defines.COMPARE, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String addFace(String uid, String user_info, String group_id, String image) {
		String imgParam = URLEncoder.encode(FileUtil.FileToBase64(image));
		String param = "uid=" + uid + "&user_info=" + user_info + "&group_id=" + group_id + "&images=" + imgParam;
		String result = null;
		try {
			result = HttpUtil.post(Defines.ADD, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String query(String uid, String group_id) {
		String param = "uid=" + uid + "&group_id=" + group_id;
		String result = null;
		try {
			result = HttpUtil.post(Defines.QUERY, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String multi(String group_id, String img) {
		String imgParam = URLEncoder.encode(FileUtil.FileToBase64(img));
		String param = "group_id=" + group_id + "&images=" + imgParam;
		String result = null;
		try {
			result = HttpUtil.post(Defines.MULTI, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String getlist(int start, int end) {
		String param = "start=" + start + "&end=" + end;
		String result = null;
		try {
			result = HttpUtil.post(Defines.LIST, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String delete(String uid) {
		String param = "uid=" + uid;
		String result = null;
		try {
			result = HttpUtil.post(Defines.DELETE, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			return result;
		}
		return null;
	}

	@Override
	public String getUsers(String group_id, int start, int end) {
		String param = "group_id=" + group_id + "&start" + start + "&end" + end;
		String result = null;
		try {
			result = HttpUtil.post(Defines.USERS, param);
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (result != null) {
			return result;
		}
		return null;
	}
	
}
