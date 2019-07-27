/*
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.writers;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.uitplus.generators.BeanGenerator;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since 2019/7/24
 *
 */
public class APIWriter {

	public void write(String pkg, String clazz, String content) throws Exception {
		File dir = new File("src/main/java/" + pkg.replaceAll("\\.", "/"));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		File file = new File(dir, clazz + ".java");
		java.io.FileWriter writer = new java.io.FileWriter(file);
		writer.write(content);
		writer.close();
	}
	
	/*************************************************
	 * 
	 *                      Generator
	 * 
	 ***************************************************/
	
	protected static final List<String> configs = new ArrayList<String>();
	
	protected static final String prefix = "conf/beans/";
	
	static {
//		configs.add("createVMFromImage.json");
		configs.add("createVMFromISO.json");
		configs.add("startVM.json");
		configs.add("stopVM.json");
		configs.add("stopForceVM.json");
		configs.add("rebootVM.json");
		configs.add("resetVM.json");
		configs.add("suspendVM.json");
		configs.add("resumeVM.json");
		configs.add("deleteVM.json");
		configs.add("removeVM.json");
	}
	
	public static void main(String[] args) throws Exception {
		String pkgName = "com.github.uitplus.beans";
		for (String config : configs) {
			BeanGenerator gen = new BeanGenerator();
			Map<String, Object> object = JSON.parseObject(new FileInputStream(
					new File(prefix + config)), Map.class);
			APIWriter writer = new APIWriter();
	
			String clazz = config.substring(0, 1).toUpperCase()
					+ config.substring(1, config.length() - 5) + "Bean";
			
			writer.write(pkgName, clazz, gen.autoGen(pkgName, clazz, object));
		}
	}

}
