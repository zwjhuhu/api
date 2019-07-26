/*
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.generators;

import java.io.File;
import java.io.FileInputStream;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since 2019/7/24
 *
 */
public class BeanGenerator extends AbstractClassGenerator {

	public final static String COPYRIGHT = "/**\r\n" + " * Copyright (2019, ) Institute of Software, "
			+ "Chinese Academy of Sciences\r\n" + " */\n";

	public final static String PACKAGE = "package PACKAGE;\n\n";

	public final static String IMPORT = "import IMPORT;\n";

	public final static String AUTHOR = "\n\n/**\r\n" + " * @author wuheng@otcaix.iscas.ac.cn\r\n"
			+ " * @author xuyuanjia2017@otcaix.iscas.ac.cn\r\n" + " * @author xianghao16@otcaix.iscas.ac.cn\r\n"
			+ " * @author yangchen18@otcaix.iscas.ac.cn\r\n" + " * @since " + new Date() + "\r\n" + " **/";

	public final static String END_CLASS = "}\n";

	public final static String FIELD = "\n\tprotected TYPE FIELD;\n";

	public final static String DEFAULT_ANNOTATION = "";

	public final static String CLASS = "\npublic static class CLASS {\n";

	public final static String SET_METHOD = "\n\tpublic void setMNAME(TYPE FIELD) {\n"
			+ "\t\tPattern p = Pattern.compile(\"REGEXP\");\n"
			+ "\t\tif(!p.matcher(String.valueOf(FIELD)).matches()) {\n"
			+ "\t\t\tthrow new RuntimeException(\"Invalid parameter.\");\n" + "\t\t}\n" + "\t\tthis.FIELD = FIELD;\n"
			+ "\t}\n";

	public final static String GET_METHOD = "\n\tpublic TYPE getMNAME() {\n" + "\t\treturn this.FIELD;\n" + "\t}\n";

	public final static String SUBCLASS = "\npublic static class CLASS {\n";

	public final static String CONSTRUCTOR = "\n\tpublic CLASS() {\n}\n";

	static {
		packages.add(Pattern.class.getName());
	}

	public BeanGenerator(String pkg) {
		super(pkg);
	}
	/**
	 * @param pkg
	 * @param clazz
	 */
	public BeanGenerator(String pkg, String clazz) {
		super(pkg, clazz);
	}

	public String autoGen(Map<String, Object> objs, String classname) {
		return autoGen(objs, classname, false);
	}

	protected String autoGen(Map<String, Object> map, String classname, boolean subClass) {

		StringBuffer sb = new StringBuffer();
		if (!subClass) {
			sb.append(dsb).append(CLASS.replaceAll("CLASS", classname));
		} else {
			sb.append(SUBCLASS.replaceAll("CLASS", classname));
		}

		StringBuffer methods = new StringBuffer();

		StringBuffer subclass = new StringBuffer();

		StringBuffer constor = new StringBuffer();

		for (String fieldName : map.keySet()) {

			Object value = map.get(fieldName);
//
			String valueType = value.getClass().getName();
			
			String mname = fieldName.toString().substring(0, 1).toUpperCase() + fieldName.toString().substring(1);
			if (valueType.equals(JSONObject.class.getName())) {
				sb.append(FIELD.replaceAll("TYPE", mname).replaceAll("FIELD", fieldName.toString()));
				methods.append(SET_METHOD.replaceAll("TYPE", mname).replaceAll("MNAME", mname).replaceAll("FIELD",
						fieldName.toString()));
				methods.append(GET_METHOD.replaceAll("TYPE", mname).replaceAll("MNAME", mname).replaceAll("FIELD",
						fieldName.toString()));
				subclass.append(autoGen((Map) value, mname, true));
			} else if (valueType.equals(JSONArray.class.getName())) {
				String valueStr = map.get(fieldName).toString();
				String fType = "";
				if (valueStr.startsWith("[{")) {
					fType = "ArrayList<" + mname + ">";
					subclass.append(autoGen((Map) ((List) map.get(fieldName)).get(0), mname, true));
				} else if (valueStr.startsWith("[")) {
					fType = "ArrayList<String>";
				}
				sb.append(FIELD.replaceAll("TYPE", fType).replaceAll("FIELD", fieldName.toString()));
				methods.append(SET_METHOD.replaceAll("TYPE", fType).replaceAll("MNAME", mname).replaceAll("FIELD",
						fieldName.toString()));
				methods.append(GET_METHOD.replaceAll("TYPE", fType).replaceAll("MNAME", mname).replaceAll("FIELD",
						fieldName.toString()));
			} else {
				
				String fullvalue = (String) map.get(fieldName);
				int idx = fullvalue.indexOf("_");
				String classtype = fullvalue.substring(0, idx);
				String pattern = (fullvalue.indexOf("_NotNull") == -1) ?fullvalue.substring(idx + 1)
						: fullvalue.substring(idx + 1, fullvalue.length() - "_NotNull".length());
				
				if (classtype.equals("String")) {
					sb.append(FIELD.replaceAll("TYPE", String.class.getSimpleName()).replaceAll("FIELD",
							fieldName.toString()));
					methods.append(SET_METHOD.replaceAll("TYPE", String.class.getSimpleName())
							.replaceAll("REGEXP", pattern).replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
					methods.append(GET_METHOD.replaceAll("TYPE", String.class.getSimpleName())
							.replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
				} else if (classtype.equals("Long")) {
					sb.append(FIELD.replaceAll("TYPE", Long.class.getSimpleName()).replaceAll("FIELD",
							fieldName.toString()));
					methods.append(SET_METHOD.replaceAll("TYPE", Long.class.getSimpleName())
							.replaceAll("REGEXP", pattern).replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
					methods.append(GET_METHOD.replaceAll("TYPE", Long.class.getSimpleName())
							.replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
				} else if (classtype.equals("Boolean")) {
					sb.append(FIELD.replaceAll("TYPE", Boolean.class.getSimpleName()).replaceAll("FIELD",
							fieldName.toString()));
					methods.append(SET_METHOD.replaceAll("TYPE", Boolean.class.getSimpleName())
							.replaceAll("REGEXP", pattern).replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
					methods.append(GET_METHOD.replaceAll("TYPE", Boolean.class.getSimpleName())
							.replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
				} 

			}
		}

		String realName = classname;
		return sb.append(CONSTRUCTOR.replaceAll("CLASS", realName).replaceAll("INITIAL", constor.toString()))
				.append(methods).append(subclass).append(END_CLASS).toString();
	}

	@SuppressWarnings("unchecked")
	@Override
	protected void doGen(Map<String, Object> objs) {
		for (String fieldName : objs.keySet()) {
			String fieldClass = objs.get(fieldName).getClass().getName();

			String value = (String) objs.get(fieldName);
			int idx = value.indexOf("_");
			String type = value.substring(0, idx);

			dsb.append(FIELD.replace("TYPE", type).replace("FIELD", fieldName));
			String pattern = (value.indexOf("_NotNull") == -1) ? value.substring(idx + 1)
					: value.substring(idx + 1, value.length() - "_NotNull".length());

			if (fieldClass.equals(String.class.getName())) {

				msb.append(SET_METHOD.replaceAll("MNAME", getMethodName(fieldName)).replaceAll("FIELD", fieldName)
						.replace("TYPE", type).replaceAll("REGEXP", pattern));
				msb.append(GET_METHOD.replaceAll("MNAME", getMethodName(fieldName)).replaceAll("FIELD", fieldName)
						.replace("TYPE", type).replaceAll("REGEXP", pattern));
			} else if (fieldClass.equals(JSONObject.class.getName())) {
				type = fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
				dsb.append(FIELD.replace("TYPE", type).replace("FIELD", fieldName));
				msb.append(SET_METHOD.replaceAll("MNAME", getMethodName(fieldName)).replaceAll("FIELD", fieldName)
						.replace("TYPE", type).replaceAll("REGEXP", pattern));
				msb.append(GET_METHOD.replaceAll("MNAME", getMethodName(fieldName)).replaceAll("FIELD", fieldName)
						.replace("TYPE", type).replaceAll("REGEXP", pattern));

				doGen((Map<String, Object>) objs.get(fieldName));
			} else {
				System.out.println(fieldName);
			}
		}
	}

	public static void main(String[] args) throws Exception {
		BeanGenerator gen = new BeanGenerator("com.github.uitplus.beans", "CreateVMFromImage");
		Map<String, Object> object = JSON.parseObject(new FileInputStream(new File("conf/createVMFromImage.json")),
				Map.class);
		System.out.println(gen.autoGen(object, "CreateVMFromImage"));
	}

}
