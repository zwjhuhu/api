/*
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.generators;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since 2019/7/24
 * This class is used for generating various classes automatically. <br>
 * A typical class includes: <br>
 * 1. copyright
 * 2. package name <br>
 * 3. import packages <br>
 * 4. authors <br>
 * 5. class annotations <br>
 * 6. class <br>
 * 
 */
public abstract class AbstractClassGenerator {

	public final static String COPYRIGHT = "/**\r\n" + " * Copyright (2019, ) Institute of Software, "
									+ "Chinese Academy of Sciences\r\n" + " */\n";

	public final static String PACKAGE = "package PACKAGE;\n\n";
	
	public final static String IMPORT = "import IMPORT;\n";
	
	public final static String AUTHOR = "\n\n/**\r\n" 
									+ " * @author wuheng@otcaix.iscas.ac.cn\r\n"
									+ " * @author xuyuanjia2017@otcaix.iscas.ac.cn\r\n" 
									+ " * @author xianghao16@otcaix.iscas.ac.cn\r\n" 
									+ " * @author yangchen18@otcaix.iscas.ac.cn\r\n" 
									+ " * @since " + new Date() + "\r\n"
									+ " **/";
	
	public final static String END_CLASS = "}\n";
	
	public final static String FIELD = "\n\tprotected TYPE FIELD;\n";
	
	public final static String DEFAULT_ANNOTATION = "";
	
	public final static String CLASS = "\npublic static class CLASS {\n";

	protected final static Set<String> packages = new HashSet<String>();
	
	static {
		packages.add(List.class.getName());
		packages.add(ArrayList.class.getName());
	}
	
	protected final StringBuffer dsb = new StringBuffer();
	
	protected final StringBuffer msb = new StringBuffer();
	
	
	public AbstractClassGenerator(String pkg) {
	}
	/**
	 * @param pkg
	 * @param clazz
	 */
	public AbstractClassGenerator(String pkg, String clazz) {
		this(pkg, clazz, false);
	}
	
	public AbstractClassGenerator(String pkg, String clazz, boolean isStatic) {
		dsb.append(COPYRIGHT)
			.append(PACKAGE.replaceAll("PACKAGE", pkg))
			.append(getImports())
			.append(AUTHOR);
		if (isStatic) {
			dsb.append(CLASS.replaceAll("CLASS", clazz));
		} else {
			dsb.append(CLASS.replace("static", "").replaceAll("CLASS", clazz));
		}
	}
	
	/**
	 * @return imports
	 */
	public String getImports() {
		StringBuffer sb = new StringBuffer();
		for (String pkg : packages) {
			sb.append(IMPORT.replaceAll("IMPORT", pkg));
		}
		return sb.toString();
	}
	
	public String autoGen(Map<String, Object> objs) {
		doGen(objs);
		return dsb.append(msb).append(END_CLASS).toString();
	}
	/**
	 * @param objs
	 * @return
	 */
	protected abstract void doGen(Map<String, Object> objs);
	
	/**
	 * @return package list
	 */
	protected void addPackage(String name) {
		packages.add(name);
	}
	
	public static String getMethodName(String fieldName) {
		return fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
	}
	
}
