/*
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.writers;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.github.uitplus.generators.BeanGenerator;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since 2019/7/24
 *
 */
public class FileWriter {

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
	
	public static void main(String[] args) throws Exception {
		BeanGenerator gen = new BeanGenerator("com.github.uitplus.beans", "CreateVMFromImage");
		Map<String, Object> object = JSON.parseObject(new FileInputStream(
				new File("conf/createVMFromImage.json")), Map.class);
		
		FileWriter writer = new FileWriter();
		writer.write("com.github.uitplus.beans", 
				"CreateVMFromImage", gen.autoGen(object));
	}

}
