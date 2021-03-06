package com.qqduan.faceRecog.core;

import java.io.File;

import org.dom4j.Element;

import com.qqduan.faceRecog.util.FileUtil;
import com.qqduan.faceRecog.util.XmlUtil;

public class Defines {

	public static final String TMPPATH = FileUtil.getAppRoot() + File.separator + "resource" + File.separator
			+ "tmp.txt";
	public static String path = getPath();
	public static String group_id = getId();
	public static String TOKEN;

	public static final String DETECT = "https://aip.baidubce.com/rest/2.0/face/v2/detect";// 人脸检测
	public static final String COMPARE = "https://aip.baidubce.com/rest/2.0/face/v2/match";// 人脸对比
	public static final String ADD = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/add";// 增加人脸
	public static final String IDENTIFY = "https://aip.baidubce.com/rest/2.0/face/v2/identify";// 人脸识别
	public static final String VERIFY = "https://aip.baidubce.com/rest/2.0/face/v2/verify";// 人脸认证
	public static final String MULTI = "https://aip.baidubce.com/rest/2.0/face/v2/multi-identify";// M:N
	public static final String UPDATE = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/update";// 人脸更新
	public static final String DELETE = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/delete";// 人脸删除
	public static final String QUERY = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/user/get";// 人脸信息查询
	public static final String LIST = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getlist";// 组列表查询
	public static final String USERS = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/getusers";// 组内用户查询
	public static final String ADDUSER = "https://aip.baidubce.com/rest/2.0/face/v2/faceset/group/adduser";// 组间复制用户

	public static String getPath() {
		Element root = XmlUtil.get(FileUtil.getAppRoot() + File.separator + "config.xml");
		String result = root.element("path").getData().toString();
		String replaceAll = result.replaceAll("\\\\", "/");
		return replaceAll;
	}

	public static String getId() {
		Element root = XmlUtil.get(FileUtil.getAppRoot() + File.separator + "config.xml");
		String result = root.element("group_id").getData().toString();
		return result;
	}

	public static void main(String[] args) {
		System.out.println(path);
		System.out.println(group_id);
	}
}
