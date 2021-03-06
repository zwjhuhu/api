/*
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.generators;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since 2019/7/24
 *
 */
public class BeanGenerator {

	public final static String COPYRIGHT = "/**\r\n" + " * Copyright (2019, ) Institute of Software, "
			+ "Chinese Academy of Sciences\r\n" + " */\n";

	public final static String PACKAGE = "package PACKAGE;\n\n";

	public final static String IMPORT = "import IMPORT;\n";

	public final static String AUTHOR = "\n\n/**\r\n" + " * @author wuheng@otcaix.iscas.ac.cn\r\n"
			+ " * @author xuyuanjia2017@otcaix.iscas.ac.cn\r\n" + " * @author xianghao16@otcaix.iscas.ac.cn\r\n"
			+ " * @author yangchen18@otcaix.iscas.ac.cn\r\n" + " * @since " + new Date() + "\r\n" + " **/";


	public final static String CLASS = "\npublic class CLASS {\n";
	
	public final static String SUBCLASS = "\n\tpublic static class CLASS {\n";
	
	public final static String END_CLASS = "}\n";
	
	public final static String CONSTRUCTOR = "\n\tpublic CLASS() {\n\t}\n";
	
	public final static String FIELD = "\n\tprotected TYPE FIELD;\n";
	
	public final static String OBJ_SET_METHOD = "\n\tpublic void setMNAME(TYPE FIELD) {\n"
			+ "\t\tthis.FIELD = FIELD;\n"
			+ "\t}\n";
	
	public final static String SET_METHOD = "\n\tpublic void setMNAME(TYPE FIELD) {\n"
			+ "\t\tPattern p = Pattern.compile(\"REGEXP\");\n"
			+ "\t\tif(!p.matcher(String.valueOf(FIELD)).matches()) {\n"
			+ "\t\t\tthrow new RuntimeException(\"Invalid parameter.\");\n" + "\t\t}\n" + "\t\tthis.FIELD = FIELD;\n"
			+ "\t}\n";

	public final static String GET_METHOD = "\n\tpublic TYPE getMNAME() {\n" + "\t\treturn this.FIELD;\n" + "\t}\n";

	
	public final static Set<String> imports = new HashSet<String>();
	
	static {
		imports.add(Pattern.class.getName());
		imports.add(ArrayList.class.getName());
	}

	public String autoGen(String pkg, String classname, Map<String, Object> objs) {
		StringBuffer csb = new StringBuffer();
		csb.append(COPYRIGHT).append(PACKAGE.replace("PACKAGE", pkg));
		for (String imp : imports) {
			csb.append(IMPORT.replaceAll("IMPORT", imp));
		}
		csb.append(AUTHOR);
		return csb.append(autoGen(classname, false, objs)).toString();
	}

	protected String autoGen(String classname, boolean subClass, Map<String, Object> map) {
		StringBuffer csb = new StringBuffer();
		if (!subClass) {
			csb.append(CLASS.replaceAll("CLASS", classname));
		} else {
			csb.append(SUBCLASS.replaceAll("CLASS", classname));
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
				csb.append(FIELD.replaceAll("TYPE", mname).replaceAll("FIELD", fieldName.toString()));
				methods.append(OBJ_SET_METHOD.replaceAll("TYPE", mname).replaceAll("MNAME", mname).replaceAll("FIELD",
						fieldName.toString()));
				methods.append(GET_METHOD.replaceAll("TYPE", mname).replaceAll("MNAME", mname).replaceAll("FIELD",
						fieldName.toString()));
				subclass.append(autoGen(mname, true, (Map) value));
			} else if (valueType.equals(JSONArray.class.getName())) {
				String valueStr = map.get(fieldName).toString();
				String fType = "";
				if (valueStr.startsWith("[{")) {
					fType = "ArrayList<" + mname + ">";
					subclass.append(autoGen(mname, true, (Map) ((List) map.get(fieldName)).get(0)));
				} else if (valueStr.startsWith("[")) {
					fType = "ArrayList<String>";
				}
				csb.append(FIELD.replaceAll("TYPE", fType).replaceAll("FIELD", fieldName.toString()));
				methods.append(OBJ_SET_METHOD.replaceAll("TYPE", fType).replaceAll("MNAME", mname).replaceAll("FIELD",
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
					csb.append(FIELD.replaceAll("TYPE", String.class.getSimpleName()).replaceAll("FIELD",
							fieldName.toString()));
					methods.append(SET_METHOD.replaceAll("TYPE", String.class.getSimpleName())
							.replaceAll("REGEXP", pattern).replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
					methods.append(GET_METHOD.replaceAll("TYPE", String.class.getSimpleName())
							.replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
				} else if (classtype.equals("Long")) {
					csb.append(FIELD.replaceAll("TYPE", Long.class.getSimpleName()).replaceAll("FIELD",
							fieldName.toString()));
					methods.append(SET_METHOD.replaceAll("TYPE", Long.class.getSimpleName())
							.replaceAll("REGEXP", pattern).replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
					methods.append(GET_METHOD.replaceAll("TYPE", Long.class.getSimpleName())
							.replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
				} else if (classtype.equals("Boolean")) {
					csb.append(FIELD.replaceAll("TYPE", Boolean.class.getSimpleName()).replaceAll("FIELD",
							fieldName.toString()));
					methods.append(SET_METHOD.replaceAll("TYPE", Boolean.class.getSimpleName())
							.replaceAll("REGEXP", pattern).replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
					methods.append(GET_METHOD.replaceAll("TYPE", Boolean.class.getSimpleName())
							.replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
				} else if (classtype.equals("Integer")) {
					csb.append(FIELD.replaceAll("TYPE", Integer.class.getSimpleName()).replaceAll("FIELD",
							fieldName.toString()));
					methods.append(SET_METHOD.replaceAll("TYPE", Integer.class.getSimpleName())
							.replaceAll("REGEXP", pattern).replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
					methods.append(GET_METHOD.replaceAll("TYPE", Integer.class.getSimpleName())
							.replaceAll("MNAME", mname).replaceAll("FIELD", fieldName.toString()));
				} 

			}
		}

		return csb.append(CONSTRUCTOR.replaceAll("CLASS", classname).replaceAll("INITIAL", constor.toString()))
				.append(methods).append(subclass).append(END_CLASS).toString();
	}

	public static void main(String[] args) throws Exception {
		BeanGenerator gen = new BeanGenerator();
		Map<String, Object> object = JSON.parseObject(new FileInputStream(new File("conf/beans/createVMFromISO.json")),
				Map.class);
		System.out.println(gen.autoGen("com.github.uitplus.beans", "CreateVMFromISOBean", object));
	}

}
