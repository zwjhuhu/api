/**
 * Copyright (2019, ) Institute of Software, Chinese Academy of Sciences
 */
package com.github.uitplus.beans;

import java.util.ArrayList;
import java.util.regex.Pattern;

import com.github.uitplus.beans.CreateVMFromISOBean.Cdroms;
import com.github.uitplus.beans.CreateVMFromISOBean.Console;
import com.github.uitplus.beans.CreateVMFromISOBean.Cpu;
import com.github.uitplus.beans.CreateVMFromISOBean.DataCloudDiskSpecifications;
import com.github.uitplus.beans.CreateVMFromISOBean.Network;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @author xuyuanjia2017@otcaix.iscas.ac.cn
 * @author xianghao16@otcaix.iscas.ac.cn
 * @author yangchen18@otcaix.iscas.ac.cn
 * @since Sat Jul 27 12:51:01 CST 2019
 **/
public class CreateVMFromImageBean {

	protected String template;

	protected Console console;

	protected ArrayList<Cdroms> cdroms;

	protected Integer memory;

	protected String os;

	protected RootCloudDiskSpecificationFromTemplate RootCloudDiskSpecificationFromTemplate;

	protected String name;

	protected Cpu cpu;

	protected ArrayList<Network> network;

	protected ArrayList<DataCloudDiskSpecifications> dataCloudDiskSpecifications;

	public CreateVMFromImageBean() {
	}

	public void setTemplate(String template) {
		Pattern p = Pattern.compile("[a-z0-9A-Z-_/./]{2,1024}");
		if (!p.matcher(String.valueOf(template)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.template = template;
	}

	public String getTemplate() {
		return this.template;
	}

	public void setConsole(Console console) {
		this.console = console;
	}

	public Console getConsole() {
		return this.console;
	}

	public void setCdroms(ArrayList<Cdroms> cdroms) {
		this.cdroms = cdroms;
	}

	public ArrayList<Cdroms> getCdroms() {
		return this.cdroms;
	}

	public void setMemory(Integer memory) {
		Pattern p = Pattern.compile("\\d{3,5}");
		if (!p.matcher(String.valueOf(memory)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.memory = memory;
	}

	public Integer getMemory() {
		return this.memory;
	}

	public void setOs(String os) {
		Pattern p = Pattern.compile("[a-z0-9A-Z-_/./]{2,18}");
		if (!p.matcher(String.valueOf(os)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.os = os;
	}

	public String getOs() {
		return this.os;
	}

	public void setRootCloudDiskSpecificationFromTemplate(
			RootCloudDiskSpecificationFromTemplate RootCloudDiskSpecificationFromTemplate) {
		this.RootCloudDiskSpecificationFromTemplate = RootCloudDiskSpecificationFromTemplate;
	}

	public RootCloudDiskSpecificationFromTemplate getRootCloudDiskSpecificationFromTemplate() {
		return this.RootCloudDiskSpecificationFromTemplate;
	}

	public void setName(String name) {
		Pattern p = Pattern.compile("[a-z0-9-]{32}");
		if (!p.matcher(String.valueOf(name)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.name = name;
	}

	public String getName() {
		return this.name;
	}

	public void setCpu(Cpu cpu) {
		this.cpu = cpu;
	}

	public Cpu getCpu() {
		return this.cpu;
	}

	public void setNetwork(ArrayList<Network> network) {
		this.network = network;
	}

	public ArrayList<Network> getNetwork() {
		return this.network;
	}

	public void setDataCloudDiskSpecifications(ArrayList<DataCloudDiskSpecifications> dataCloudDiskSpecifications) {
		this.dataCloudDiskSpecifications = dataCloudDiskSpecifications;
	}

	public ArrayList<DataCloudDiskSpecifications> getDataCloudDiskSpecifications() {
		return this.dataCloudDiskSpecifications;
	}

	public static class RootCloudDiskSpecificationFromTemplate {

		protected Long writeSpeed;

		protected Long readingSpeed;

		protected Long capacity;

		public RootCloudDiskSpecificationFromTemplate() {
		}

		public void setWriteSpeed(Long writeSpeed) {
			Pattern p = Pattern.compile("\\d{8,10}");
			if (!p.matcher(String.valueOf(writeSpeed)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.writeSpeed = writeSpeed;
		}

		public Long getWriteSpeed() {
			return this.writeSpeed;
		}

		public void setReadingSpeed(Long readingSpeed) {
			Pattern p = Pattern.compile("\\d{8,10}");
			if (!p.matcher(String.valueOf(readingSpeed)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.readingSpeed = readingSpeed;
		}

		public Long getReadingSpeed() {
			return this.readingSpeed;
		}

		public void setCapacity(Long capacity) {
			Pattern p = Pattern.compile("\\d{2,5}");
			if (!p.matcher(String.valueOf(capacity)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.capacity = capacity;
		}

		public Long getCapacity() {
			return this.capacity;
		}
	}

}
