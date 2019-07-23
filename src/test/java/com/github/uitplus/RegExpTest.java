/**
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus;

import java.util.regex.Pattern;

/**
 * @author wuheng
 * @since 2019.7.23
 */
public class RegExpTest {

	protected static String NAME_PATTERN = "[a-z0-9-]{32}";
	
	protected static String PATH_PATTERN = "[a-z0-9/.:-]{1,1024}";
	
	protected static String NUMB_PATTERN = "[1-9]|[10-99]\\d";
	
	protected static String ENUM_PATTERN = "qcow2|raw";

	public static void main(String[] args) {
//		checkName();
//		checkPath();
//		checkNumber();
		checkEnum();
	}

	protected static void checkEnum() {
		Pattern r = Pattern.compile(ENUM_PATTERN);
		System.out.println(r.matcher("qcow2").matches());
	}

	protected static void checkNumber() {
		Pattern r = Pattern.compile(NUMB_PATTERN);
		System.out.println(r.matcher("0").matches());
	}

	protected static void checkPath() {
		Pattern r = Pattern.compile(PATH_PATTERN);
		System.out.println(r.matcher("A").matches());
	}

	protected static void checkName() {
		Pattern r = Pattern.compile(NAME_PATTERN);
		System.out.println(r.matcher("650646e8c17a49d0b83c1c797811e064").matches());
	}

}
