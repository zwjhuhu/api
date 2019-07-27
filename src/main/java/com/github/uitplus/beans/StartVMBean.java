/**
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.beans;

import java.util.regex.Pattern;
import java.util.ArrayList;


/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @author xuyuanjia2017@otcaix.iscas.ac.cn
 * @author xianghao16@otcaix.iscas.ac.cn
 * @author yangchen18@otcaix.iscas.ac.cn
 * @since Sat Jul 27 12:51:01 CST 2019
 **/
public class StartVMBean {

	protected String name;

	public StartVMBean() {
	}

	public void setName(String name) {
		Pattern p = Pattern.compile("[a-z0-9-]{32}");
		if(!p.matcher(String.valueOf(name)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.name = name;
	}

	public String getName() {
		return this.name;
	}
}
