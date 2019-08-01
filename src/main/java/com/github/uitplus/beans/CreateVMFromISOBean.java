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
 * @since Thu Aug 01 08:34:38 CST 2019
 **/
public class CreateVMFromISOBean {

	protected Console console;

	protected ArrayList<Cdroms> cdroms;

	protected Integer memory;

	protected String os;

	protected String iso;

	protected String name;

	protected Cpu cpu;

	protected RootCloudDiskSpecificationFromISO RootCloudDiskSpecificationFromISO;

	protected ArrayList<Network> network;

	protected ArrayList<DataCloudDiskSpecifications> dataCloudDiskSpecifications;

	public CreateVMFromISOBean() {
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

	public void setIso(String iso) {
		Pattern p = Pattern.compile("[a-z0-9A-Z-_/./]{2,1024}");
		if (!p.matcher(String.valueOf(iso)).matches()) {
			throw new RuntimeException("Invalid parameter.");
		}
		this.iso = iso;
	}

	public String getIso() {
		return this.iso;
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

	public void setRootCloudDiskSpecificationFromISO(
			RootCloudDiskSpecificationFromISO RootCloudDiskSpecificationFromISO) {
		this.RootCloudDiskSpecificationFromISO = RootCloudDiskSpecificationFromISO;
	}

	public RootCloudDiskSpecificationFromISO getRootCloudDiskSpecificationFromISO() {
		return this.RootCloudDiskSpecificationFromISO;
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

	public static class Console {

		protected Spice spice;

		protected Vnc vnc;

		public Console() {
		}

		public void setSpice(Spice spice) {
			this.spice = spice;
		}

		public Spice getSpice() {
			return this.spice;
		}

		public void setVnc(Vnc vnc) {
			this.vnc = vnc;
		}

		public Vnc getVnc() {
			return this.vnc;
		}

		public static class Spice {

			protected String consolePassword;

			public Spice() {
			}

			public void setConsolePassword(String consolePassword) {
				Pattern p = Pattern.compile("[a-z0-9A_Z]{6,18}");
				if (!p.matcher(String.valueOf(consolePassword)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.consolePassword = consolePassword;
			}

			public String getConsolePassword() {
				return this.consolePassword;
			}
		}

		public static class Vnc {

			protected String consolePassword;

			public Vnc() {
			}

			public void setConsolePassword(String consolePassword) {
				Pattern p = Pattern.compile("[a-z0-9A_Z]{6,18}");
				if (!p.matcher(String.valueOf(consolePassword)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.consolePassword = consolePassword;
			}

			public String getConsolePassword() {
				return this.consolePassword;
			}
		}
	}

	public static class Cdroms {

		protected String iso;

		protected String name;

		public Cdroms() {
		}

		public void setIso(String iso) {
			Pattern p = Pattern.compile("[a-z0-9A-Z-_/./]{2,1024}");
			if (!p.matcher(String.valueOf(iso)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.iso = iso;
		}

		public String getIso() {
			return this.iso;
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
	}

	public static class Cpu {

		protected String cpuset;

		protected Integer cpunum;

		public Cpu() {
		}

		public void setCpuset(String cpuset) {
			Pattern p = Pattern.compile("[a-z0-9,-]{2,16}");
			if (!p.matcher(String.valueOf(cpuset)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.cpuset = cpuset;
		}

		public String getCpuset() {
			return this.cpuset;
		}

		public void setCpunum(Integer cpunum) {
			Pattern p = Pattern.compile("\\d{1,2}");
			if (!p.matcher(String.valueOf(cpunum)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.cpunum = cpunum;
		}

		public Integer getCpunum() {
			return this.cpunum;
		}
	}

	public static class RootCloudDiskSpecificationFromISO {

		protected Long writeSpeed;

		protected Long diskBandwidth;

		protected Long readingSpeed;

		protected Long capacity;

		public RootCloudDiskSpecificationFromISO() {
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

		public void setDiskBandwidth(Long diskBandwidth) {
			Pattern p = Pattern.compile("\\d{8,10}");
			if (!p.matcher(String.valueOf(diskBandwidth)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.diskBandwidth = diskBandwidth;
		}

		public Long getDiskBandwidth() {
			return this.diskBandwidth;
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

	public static class Network {

		protected Long upstreamBandwidth;

		protected Long downstreamBandwidth;

		protected String bridge;

		public Network() {
		}

		public void setUpstreamBandwidth(Long upstreamBandwidth) {
			Pattern p = Pattern.compile("\\d{8,10}");
			if (!p.matcher(String.valueOf(upstreamBandwidth)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.upstreamBandwidth = upstreamBandwidth;
		}

		public Long getUpstreamBandwidth() {
			return this.upstreamBandwidth;
		}

		public void setDownstreamBandwidth(Long downstreamBandwidth) {
			Pattern p = Pattern.compile("\\d{8,10}");
			if (!p.matcher(String.valueOf(downstreamBandwidth)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.downstreamBandwidth = downstreamBandwidth;
		}

		public Long getDownstreamBandwidth() {
			return this.downstreamBandwidth;
		}

		public void setBridge(String bridge) {
			Pattern p = Pattern.compile("[a-zA-Z0-9]{2,16}");
			if (!p.matcher(String.valueOf(bridge)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.bridge = bridge;
		}

		public String getBridge() {
			return this.bridge;
		}
	}

	public static class DataCloudDiskSpecifications {

		protected String path;

		protected Long writeSpeed;

		protected Long diskBandwidth;

		protected Long readingSpeed;

		protected Long capacity;

		public DataCloudDiskSpecifications() {
		}

		public void setPath(String path) {
			Pattern p = Pattern.compile("[a-z0-9A-Z-_/./]{2,1024}");
			if (!p.matcher(String.valueOf(path)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.path = path;
		}

		public String getPath() {
			return this.path;
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

		public void setDiskBandwidth(Long diskBandwidth) {
			Pattern p = Pattern.compile("\\d{8,10}");
			if (!p.matcher(String.valueOf(diskBandwidth)).matches()) {
				throw new RuntimeException("Invalid parameter.");
			}
			this.diskBandwidth = diskBandwidth;
		}

		public Long getDiskBandwidth() {
			return this.diskBandwidth;
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
