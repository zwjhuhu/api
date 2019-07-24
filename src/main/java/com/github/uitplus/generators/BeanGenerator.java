/*
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.generators;

import java.io.File;
import java.io.FileInputStream;
import java.util.Map;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since 2019/7/24
 *
 */
public class BeanGenerator extends AbstractClassGenerator {

	public final static String SET_METHOD = "\n\tpublic void setMNAME(TYPE NAME) {\n"
											+ "\t\tPattern p = Pattern.compile(\"REGEXP\");\n"
											+ "\t\tif(!p.matcher(String.valueOf(NAME)).matches()) {\n"
											+ "\t\t\tthrow new RuntimeException(\"Invalid parameter.\");\n"
											+ "\t\t}\n"
											+ "\t\tthis.NAME = NAME;\n"
											+ "\t}\n";
	
	public final static String GET_METHOD = "\n\tpublic TYPE getMNAME() {\n"
											+ "\t\treturn this.NAME;\n"
											+ "\t}\n";

	static {
		packages.add(Pattern.class.getName());
	}
	/**
	 * @param pkg
	 * @param clazz
	 */
	public BeanGenerator(String pkg, String clazz) {
		super(pkg, clazz);
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doGen(Map<String, Object> objs) {
		for (String fieldName : objs.keySet()) {
			String fieldClass = objs.get(fieldName).getClass().getName();
			if (fieldClass.equals(String.class.getName())) {
				String value = (String) objs.get(fieldName);
				int idx = value.indexOf("_");
				String type = value.substring(0, idx);
				dsb.append(FIELD.replace("TYPE", type)
						.replace("FIELD", fieldName));
				String pattern = (value.indexOf("_NotNull") == -1) 
						? value.substring(idx + 1)
								: value.substring(idx + 1, value.length() - "_NotNull".length());
				msb.append(SET_METHOD.replaceAll("MNAME", getMethodName(fieldName))
						.replaceAll("NAME", fieldName).replace("TYPE", type).replaceAll("REGEXP", pattern));
				msb.append(GET_METHOD.replaceAll("MNAME", getMethodName(fieldName))
						.replaceAll("NAME", fieldName).replace("TYPE", type).replaceAll("REGEXP", pattern));
			} else if (fieldClass.equals(JSONObject.class.getName())) {
				doGen((Map<String, Object>) objs.get(fieldName));
			}
		}
	}

	
	public static void main(String[] args) throws Exception {
		BeanGenerator gen = new BeanGenerator("com.github.uitplus.beans", "CreateVMFromImage");
		Map<String, Object> object = JSON.parseObject(new FileInputStream(
				new File("conf/createVMFromImage.json")), Map.class);
		System.out.println(gen.autoGen(object));
	}

}
