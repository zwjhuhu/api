package com.github.uitplus.beans;

import java.util.ArrayList;
import java.util.regex.Pattern;

/**
 * @author wuheng@otcaix.iscas.ac.cn
 * @since 2019/7/24
 *
 */

public class CreateVMFromImage {

	protected CreateAndStartVMFromTemplate CreateAndStartVMFromTemplate;

	protected String name;

	public CreateVMFromImage() {
	}

	public void setCreateAndStartVMFromTemplate(CreateAndStartVMFromTemplate CreateAndStartVMFromTemplate) {
		this.CreateAndStartVMFromTemplate = CreateAndStartVMFromTemplate;
	}

	public CreateAndStartVMFromTemplate getCreateAndStartVMFromTemplate() {
		return this.CreateAndStartVMFromTemplate;
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

	public static class CreateAndStartVMFromTemplate {

		protected Console console;

		protected ArrayList<Cdroms> cdroms;

		protected RootCloudDiskSpecificationFromTemplate RootCloudDiskSpecificationFromTemplate;

		protected Basic basic;

		protected ArrayList<Network> network;

		protected ArrayList<DataCloudDiskSpecifications> dataCloudDiskSpecifications;

		public CreateAndStartVMFromTemplate() {
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

		public void setRootCloudDiskSpecificationFromTemplate(
				RootCloudDiskSpecificationFromTemplate RootCloudDiskSpecificationFromTemplate) {
			this.RootCloudDiskSpecificationFromTemplate = RootCloudDiskSpecificationFromTemplate;
		}

		public RootCloudDiskSpecificationFromTemplate getRootCloudDiskSpecificationFromTemplate() {
			return this.RootCloudDiskSpecificationFromTemplate;
		}

		public void setBasic(Basic basic) {
			this.basic = basic;
		}

		public Basic getBasic() {
			return this.basic;
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
					Pattern p = Pattern.compile("[a-z0-9A_Z-/.]{6,18}");
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
					Pattern p = Pattern.compile("[a-z0-9A_Z-/.]{6,18}");
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
				Pattern p = Pattern.compile("[a-z0-9A_Z-/.]{2,1024}");
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

		public static class RootCloudDiskSpecificationFromTemplate {

			protected String template;

			protected Long writeSpeed;

			protected Long readingSpeed;

			protected String target;

			public RootCloudDiskSpecificationFromTemplate() {
			}

			public void setTemplate(String template) {
				Pattern p = Pattern.compile("[a-z0-9A_Z-/.]{2,1024}");
				if (!p.matcher(String.valueOf(template)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.template = template;
			}

			public String getTemplate() {
				return this.template;
			}

			public void setWriteSpeed(Long writeSpeed) {
				Pattern p = Pattern.compile("[10240000-99999999]d|[102400000-204800000]d");
				if (!p.matcher(String.valueOf(writeSpeed)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.writeSpeed = writeSpeed;
			}

			public Long getWriteSpeed() {
				return this.writeSpeed;
			}

			public void setReadingSpeed(Long readingSpeed) {
				Pattern p = Pattern.compile("[10240000-99999999]d|[102400000-204800000]d");
				if (!p.matcher(String.valueOf(readingSpeed)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.readingSpeed = readingSpeed;
			}

			public Long getReadingSpeed() {
				return this.readingSpeed;
			}

			public void setTarget(String target) {
				Pattern p = Pattern.compile("[a-z0-9A_Z-/.]{2,1024}");
				if (!p.matcher(String.valueOf(target)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.target = target;
			}

			public String getTarget() {
				return this.target;
			}
		}

		public static class Basic {

			protected String cpuset;

			public Basic() {
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
		}

		public static class Network {

			protected Long upstreamBandwidth;

			protected String name;

			protected Long downstreamBandwidth;

			public Network() {
			}

			public void setUpstreamBandwidth(Long upstreamBandwidth) {
				Pattern p = Pattern.compile("[100-999]d|[1000-1024]d");
				if (!p.matcher(String.valueOf(upstreamBandwidth)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.upstreamBandwidth = upstreamBandwidth;
			}

			public Long getUpstreamBandwidth() {
				return this.upstreamBandwidth;
			}

			public void setName(String name) {
				Pattern p = Pattern.compile("[a-z0-9]{2,16}");
				if (!p.matcher(String.valueOf(name)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.name = name;
			}

			public String getName() {
				return this.name;
			}

			public void setDownstreamBandwidth(Long downstreamBandwidth) {
				Pattern p = Pattern.compile("[100-999]d|[1000-1024]d");
				if (!p.matcher(String.valueOf(downstreamBandwidth)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.downstreamBandwidth = downstreamBandwidth;
			}

			public Long getDownstreamBandwidth() {
				return this.downstreamBandwidth;
			}
		}

		public static class DataCloudDiskSpecifications {

			protected Long writeSpeed;

			protected Long readingSpeed;

			protected Long capacity;

			public DataCloudDiskSpecifications() {
			}

			public void setWriteSpeed(Long writeSpeed) {
				Pattern p = Pattern.compile("[10240000-99999999]d|[102400000-204800000]d");
				if (!p.matcher(String.valueOf(writeSpeed)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.writeSpeed = writeSpeed;
			}

			public Long getWriteSpeed() {
				return this.writeSpeed;
			}

			public void setReadingSpeed(Long readingSpeed) {
				Pattern p = Pattern.compile("[10240000-99999999]d|[102400000-204800000]d");
				if (!p.matcher(String.valueOf(readingSpeed)).matches()) {
					throw new RuntimeException("Invalid parameter.");
				}
				this.readingSpeed = readingSpeed;
			}

			public Long getReadingSpeed() {
				return this.readingSpeed;
			}

			public void setCapacity(Long capacity) {
				Pattern p = Pattern.compile("[10-99]d|[100-200]d");
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
}
