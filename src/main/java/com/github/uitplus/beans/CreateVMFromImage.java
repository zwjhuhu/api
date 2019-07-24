/**
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.beans;

import java.util.regex.Pattern;


/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @author xuyuanjia2017@otcaix.iscas.ac.cn
 * @author xianghao16@otcaix.iscas.ac.cn
 * @author yangchen18@otcaix.iscas.ac.cn
 * @since Wed Jul 24 22:57:44 CST 2019
 **/
public class CreateVMFromImage {

	protected String name;

	protected Long capacity;

	protected Long upstreamBandwidth;

	protected Long writeSpeed;

	protected Integer memory;

	protected Long diskBandwidth;

	protected Integer cpu;

	protected Long downstreamBandwidth;

	protected Long readingSpeed;

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

	public void setCapacity(Long capacity) {
		Pattern p = Pattern.compile("[10240-99999]d|[100000-204800]d");
		if(!p.matcher(String.valueOf(capacity)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.capacity = capacity;
	}

	public Long getCapacity() {
		return this.capacity;
	}

	public void setUpstreamBandwidth(Long upstreamBandwidth) {
		Pattern p = Pattern.compile("[10-99]|d[100-999]d|[1000-1024]d");
		if(!p.matcher(String.valueOf(upstreamBandwidth)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.upstreamBandwidth = upstreamBandwidth;
	}

	public Long getUpstreamBandwidth() {
		return this.upstreamBandwidth;
	}

	public void setWriteSpeed(Long writeSpeed) {
		Pattern p = Pattern.compile("[10-99]d|[100-999]d|[1000-1024]d");
		if(!p.matcher(String.valueOf(writeSpeed)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.writeSpeed = writeSpeed;
	}

	public Long getWriteSpeed() {
		return this.writeSpeed;
	}

	public void setMemory(Integer memory) {
		Pattern p = Pattern.compile("[512-999]d|[1000-9999]d|[10000-32768]d");
		if(!p.matcher(String.valueOf(memory)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.memory = memory;
	}

	public Integer getMemory() {
		return this.memory;
	}

	public void setDiskBandwidth(Long diskBandwidth) {
		Pattern p = Pattern.compile("[512-999]d|[1000-9999]d|[10000-32768]d");
		if(!p.matcher(String.valueOf(diskBandwidth)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.diskBandwidth = diskBandwidth;
	}

	public Long getDiskBandwidth() {
		return this.diskBandwidth;
	}

	public void setCpu(Integer cpu) {
		Pattern p = Pattern.compile("[1-16]");
		if(!p.matcher(String.valueOf(cpu)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.cpu = cpu;
	}

	public Integer getCpu() {
		return this.cpu;
	}

	public void setDownstreamBandwidth(Long downstreamBandwidth) {
		Pattern p = Pattern.compile("[10-99]|d[100-999]d|[1000-1024]d");
		if(!p.matcher(String.valueOf(downstreamBandwidth)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.downstreamBandwidth = downstreamBandwidth;
	}

	public Long getDownstreamBandwidth() {
		return this.downstreamBandwidth;
	}

	public void setReadingSpeed(Long readingSpeed) {
		Pattern p = Pattern.compile("[100-999]d|[1000-1024]d");
		if(!p.matcher(String.valueOf(readingSpeed)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.readingSpeed = readingSpeed;
	}

	public Long getReadingSpeed() {
		return this.readingSpeed;
	}
}
